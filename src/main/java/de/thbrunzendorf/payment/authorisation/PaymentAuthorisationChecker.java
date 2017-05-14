package de.thbrunzendorf.payment.authorisation;

import de.thbrunzendorf.payment.value.Money;
import de.thbrunzendorf.payment.value.Payment;
import de.thbrunzendorf.payment.value.User;

/**
 * Created by Thorsten on 14.05.2017.
 */
public class PaymentAuthorisationChecker {

    public PaymentAuthorisation checkFor(Payment payment) {
        PaymentAuthorisation paymentAuthorisation = new PaymentAuthorisation();
        User initiator = payment.getInitiator();
        Money amount = payment.getAmount();
        Money limit = initiator.getLimit();
        if (amount.compareTo(limit) <= 0) {
            paymentAuthorisation.setApprovalNeeded(false);
        }
        if (amount.compareTo(limit) > 0) {
            paymentAuthorisation.setApprovalNeeded(true);
            User approver = getPrimaryApprover(initiator, amount);
            paymentAuthorisation.setPrimaryApprover(approver);
        }
        return paymentAuthorisation;
    }

    private User getPrimaryApprover(User initiator, Money amount) {
        User supervisor = initiator.getSupervisor();
        Money limit = supervisor.getLimit();
        int maxIterations = 10; // preventing infinite loops
        for (int i = 0; i < maxIterations; i++) {
            if (amount.compareTo(limit) > 0) {
                supervisor = supervisor.getSupervisor();
                limit = supervisor.getLimit();
            }
        }
        return supervisor;
    }
}

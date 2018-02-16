package de.thbrunzendorf.payment.authorisation;

import de.thbrunzendorf.payment.value.Money;
import de.thbrunzendorf.payment.value.Payment;
import de.thbrunzendorf.payment.value.User;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thorsten on 14.05.2017.
 */
public class PaymentAuthorisationCheckerJUnit4Test {

    @Test
    public void paymentByInitiatorWithLimitMoreThanAmountDoesNotNeedApproval() throws Exception {
        User initiator = User.withNameAndLimit("dave", Money.withAmount(200, 0));
        Payment payment = new Payment(Money.withAmount(123, 45), initiator);

        PaymentAuthorisationChecker checker = new PaymentAuthorisationChecker();
        PaymentAuthorisation authorisation = checker.checkFor(payment);

        assertFalse(authorisation.isApprovalNeeded());
    }

    @Test
    public void paymentByInitiatorWithLimitLessThanAmountNeedsApprovalBySupervisor() throws Exception {
        User supervisor = User.withNameAndLimit("liz", Money.withAmount(200, 0));
        User initiator = User.withNameAndLimitAndSupervisor("dave", Money.withAmount(100, 0), supervisor);
        Payment payment = new Payment(Money.withAmount(123, 45), initiator);

        PaymentAuthorisationChecker checker = new PaymentAuthorisationChecker();
        PaymentAuthorisation authorisation = checker.checkFor(payment);

        assertTrue(authorisation.isApprovalNeeded());
        System.out.println(authorisation.getPrimaryApprover().getName());
    }

    @Test
    public void paymentByInitiatorAndSupervisorWithLimitLessThanAmountNeedsApprovalBySupervisorOfSupervisor() throws Exception {
        User supervisorOfSupervisor = User.withNameAndLimit("mary", Money.withAmount(200, 0));
        User supervisor = User.withNameAndLimitAndSupervisor("liz", Money.withAmount(100, 0), supervisorOfSupervisor);
        User initiator = User.withNameAndLimitAndSupervisor("dave", Money.withAmount(50, 0), supervisor);
        Payment payment = new Payment(Money.withAmount(123, 45), initiator);

        PaymentAuthorisationChecker checker = new PaymentAuthorisationChecker();
        PaymentAuthorisation authorisation = checker.checkFor(payment);

        assertTrue(authorisation.isApprovalNeeded());
        System.out.println(authorisation.getPrimaryApprover().getName());
    }
}

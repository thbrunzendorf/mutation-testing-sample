package de.thbrunzendorf.payment.authorisation;

import de.thbrunzendorf.payment.authorisation.PaymentAuthorisation;
import de.thbrunzendorf.payment.authorisation.PaymentAuthorisationChecker;
import de.thbrunzendorf.payment.value.Money;
import de.thbrunzendorf.payment.value.Payment;
import de.thbrunzendorf.payment.value.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thorsten on 14.05.2017.
 */
public class PaymentAuthorisationCheckerTest {

    @Test
    public void paymentByInitiatorWithLimitMoreThanAmountDoesNotNeedApproval() throws Exception {
        User supervisor = User.withNameAndLimit("liz", Money.withAmount(500, 00));
        User initiator = User.withNameAndLimitAndSupervisor("dave", Money.withAmount(200, 00), supervisor);
        Payment payment = new Payment(Money.withAmount(123, 45), initiator);

        PaymentAuthorisationChecker checker = new PaymentAuthorisationChecker();
        PaymentAuthorisation authorisation = checker.checkFor(payment);

        assertFalse(authorisation.isApprovalNeeded());
    }

    @Test
    public void paymentByInitiatorWithLimitLessThanAmountNeedsApprovalBySupervisor() throws Exception {
        User supervisor = User.withNameAndLimit("liz", Money.withAmount(200, 00));
        User initiator = User.withNameAndLimitAndSupervisor("dave", Money.withAmount(100, 00), supervisor);
        Payment payment = new Payment(Money.withAmount(123, 45), initiator);

        PaymentAuthorisationChecker checker = new PaymentAuthorisationChecker();
        PaymentAuthorisation authorisation = checker.checkFor(payment);

        assertTrue(authorisation.isApprovalNeeded());
        assertEquals("liz", authorisation.getPrimaryApprover().getName());
    }

    @Test
    public void paymentByInitiatorAndSupervisorWithLimitLessThanAmountNeedsApprovalBySupervisorOfSupervisor() throws Exception {
        User supervisorOfSupervisor = User.withNameAndLimit("mary", Money.withAmount(200, 00));
        User supervisor = User.withNameAndLimitAndSupervisor("liz", Money.withAmount(100, 00), supervisorOfSupervisor);
        User initiator = User.withNameAndLimitAndSupervisor("dave", Money.withAmount(50, 00), supervisor);
        Payment payment = new Payment(Money.withAmount(123, 45), initiator);

        PaymentAuthorisationChecker checker = new PaymentAuthorisationChecker();
        PaymentAuthorisation authorisation = checker.checkFor(payment);

        assertTrue(authorisation.isApprovalNeeded());
        assertEquals("mary", authorisation.getPrimaryApprover().getName());
    }
}

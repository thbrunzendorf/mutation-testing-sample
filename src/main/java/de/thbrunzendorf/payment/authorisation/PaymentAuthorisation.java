package de.thbrunzendorf.payment.authorisation;

import de.thbrunzendorf.payment.value.User;

/**
 * Created by Thorsten on 14.05.2017.
 */
public class PaymentAuthorisation {

    private boolean approvalNeeded;
    private User primaryApprover;

    public void setApprovalNeeded(boolean approvalNeeded) {
        this.approvalNeeded = approvalNeeded;
    }

    public boolean isApprovalNeeded() {
        return approvalNeeded;
    }

    public void setPrimaryApprover(User primaryApprover) {
        this.primaryApprover = primaryApprover;
    }

    public User getPrimaryApprover() {
        return primaryApprover;
    }
}

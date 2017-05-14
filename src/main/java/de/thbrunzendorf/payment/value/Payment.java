package de.thbrunzendorf.payment.value;

/**
 * Created by Thorsten on 14.05.2017.
 */
public class Payment {

    // naive implementation - more attributes needed e.g. payee
    private Money amount;
    private User initiator;

    public Payment(Money amount, User initiator) {
        this.amount = amount;
        this.initiator = initiator;
    }

    public Money getAmount() {
        return amount;
    }

    public User getInitiator() {
        return initiator;
    }
}

package de.thbrunzendorf.payment.value;

import java.math.BigDecimal;

/**
 * Created by Thorsten on 14.05.2017.
 */
public class Money implements Comparable {

    private static final String DEFAULT_CURRENCY = "EUR";

    private final int majorAmount;
    private final int minorAmount;
    private final String currency;

    private Money (int majorAmount, int minorAmount) {
        this.majorAmount = majorAmount;
        this.minorAmount = minorAmount;
        this.currency = DEFAULT_CURRENCY;
    }

    public static Money withAmount(int majorAmount, int minorAmount) {
        return new Money(majorAmount, minorAmount);
    }

    public BigDecimal asBigDecimal() {
        int scale = 2;
        int unscaledValue = this.majorAmount * 10 * 10 + this.minorAmount;
        return BigDecimal.valueOf(unscaledValue, scale);
    }

    public int compareTo(Object o) {
        Money other = (Money) o;
        return this.asBigDecimal().compareTo(other.asBigDecimal());
    }
}

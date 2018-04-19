package com.samcgardner.zopatest.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.samcgardner.zopatest.util.Constants.MONTHS_IN_YEAR;

public class Quote {

    private final long requestedAmount;
    private final BigDecimal rate;
    private final BigDecimal monthlyRepayment;
    private final BigDecimal totalRepayment;

    public Quote(long requestedAmount, BigDecimal rate, BigDecimal monthlyRepayment, BigDecimal totalRepayment) {
        this.requestedAmount = requestedAmount;
        this.rate = rate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public long getRequestedAmount() {
        return requestedAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }

    public String prettyPrint() {
        StringBuilder builder = new StringBuilder();

        builder.append("Requested amount: \u00a3");
        builder.append(requestedAmount);
        builder.append(System.getProperty("line.separator"));

        builder.append("Rate: ");
        builder.append(formatRate(rate));
        builder.append("%");
        builder.append(System.getProperty("line.separator"));

        builder.append("Monthly repayment: \u00a3");
        builder.append(monthlyRepayment.setScale(2, RoundingMode.HALF_EVEN));
        builder.append(System.getProperty("line.separator"));

        builder.append("Total repayment: \u00a3");
        builder.append(totalRepayment.setScale(2, RoundingMode.HALF_EVEN));

        return builder.toString();
    }

    // We deal with monthly rates in decimal form, whereas our calculator expects percentage-based rates
    // approximating a yearly APR and given to a low degree of precision, so we need to convert them before giving
    // a quote
    private BigDecimal formatRate(BigDecimal rate) {
            return rate.multiply(new BigDecimal(100))
                    .multiply(MONTHS_IN_YEAR)
                    .setScale(1, RoundingMode.HALF_EVEN);
    }

}

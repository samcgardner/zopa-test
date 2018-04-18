package com.samcgardner.zopatest.calculator;

import com.samcgardner.zopatest.util.Tuple;

import java.math.BigDecimal;
import java.math.MathContext;

public class QuoteCalculator {

    private static final int LOAN_TERM = 36;

    public static Quote calculateQuote(Loan loan) {
        return new Quote(loan.getAmount(),
                (calculateAggregateRate(loan)),
                calculateTotalMonthlyRepayment(loan),
                calculateTotalRepayment(loan));
    }

    private static BigDecimal calculateTotalRepayment(Loan loan) {
        return calculateTotalMonthlyRepayment(loan).multiply(new BigDecimal(36));
    }

    // Calculate the total monthly repayment as the sum of the payments owed to each micro-lender
    private static BigDecimal calculateTotalMonthlyRepayment(Loan loan) {
        return loan.getComponents()
                .stream()
                .map(QuoteCalculator::calculateIndividualMonthlyRepayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // An implementation of the US standardized monthly payment calculation
    // as given by https://en.wikipedia.org/wiki/Mortgage_calculator#Monthly_payment_formula
    private static BigDecimal calculateIndividualMonthlyRepayment(Tuple<Long, BigDecimal> tuple) {
        BigDecimal principalAsDecimal = new BigDecimal(tuple.getLeft());
        BigDecimal cyclotom = (BigDecimal.ONE.add(tuple.getRight())).pow(LOAN_TERM);
        return (tuple.getRight().multiply(principalAsDecimal).multiply(cyclotom)).divide(cyclotom.subtract(BigDecimal.ONE), MathContext.DECIMAL128);
    }

    // Since a loan is composed of many smaller loans, each with their own interest rate, there is no single
    // interest rate. We calculate the aggregate rate by taking an average of the rates comprising the loan
    // weighted by the loan amount
    private static BigDecimal calculateAggregateRate(Loan loan) {
        BigDecimal weightedSum = loan.getComponents()
                .stream()
                .reduce(BigDecimal.ZERO,
                        (decimal, tuple) -> {
                            BigDecimal leftAsDecimal = new BigDecimal(tuple.getLeft());
                            BigDecimal weightedInterest = tuple.getRight().multiply(leftAsDecimal);
                            return decimal.add(weightedInterest);
                        },
                        BigDecimal::add);

        BigDecimal amountAsDecimal = new BigDecimal(loan.getAmount());
        return weightedSum.divide(amountAsDecimal, MathContext.DECIMAL128);
    }

}

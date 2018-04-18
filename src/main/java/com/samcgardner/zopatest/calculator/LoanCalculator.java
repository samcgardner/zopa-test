package com.samcgardner.zopatest.calculator;

import com.samcgardner.zopatest.model.Lender;
import com.samcgardner.zopatest.util.Tuple;

import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.samcgardner.zopatest.util.Constants.MONTHS_IN_YEAR;

public class LoanCalculator {


    public static Optional<Loan> calculateBestLoan(List<Lender> lenders, long amount) {

        long runningTotal = 0L;
        Loan loan = new Loan(amount);
        List<Lender> sortedLenders = lenders
                .stream()
                .sorted(Comparator.comparing(Lender::getAmountAvailable))
                .collect(Collectors.toList());

        for (Lender lender : sortedLenders) {
            long toAdd = Math.min(amount - runningTotal, lender.getAmountAvailable());
            runningTotal += toAdd;
            loan.addComponent(new Tuple<>(toAdd, lender.getInterestRate().divide(MONTHS_IN_YEAR, MathContext.DECIMAL128)));
            if (runningTotal == loan.getAmount()) {
                return Optional.of(loan);
            }
        }

        return Optional.empty();
    }


}

package com.samcgardner.zopatest.calculator;

import com.samcgardner.zopatest.model.Lender;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

public class LoanCalculatorTest {

    @Test
    public void returnsEmptyForInsufficientLoan() {
        Optional<Loan> result = LoanCalculator.calculateBestLoan(Collections.emptyList(), 1);
        assertFalse(result.isPresent());
    }

    @Test
    public void calculatesSimpleLoan() {
        List<Lender> lenders = Arrays.asList(
                new Lender("Bob", BigDecimal.ONE, 10),
                new Lender("Alice", BigDecimal.TEN, 10),
                new Lender("Zav", BigDecimal.ZERO, 10));

        Loan result = LoanCalculator.calculateBestLoan(lenders, 20).get();

        assertEquals(20, result.getAmount());

        assertEquals(BigDecimal.ZERO, result.getComponents().get(0).getRight());
        assertEquals(Long.valueOf(10), result.getComponents().get(0).getLeft());

        assertEquals(BigDecimal.ONE.divide(new BigDecimal(12), MathContext.DECIMAL128), result.getComponents().get(1).getRight());
        assertEquals(Long.valueOf(10), result.getComponents().get(1).getLeft());
    }

    @Test
    public void truncatesPartialLoansCorrectly() {
        List<Lender> lenders = Arrays.asList(
                new Lender("Bob", BigDecimal.ONE, 10),
                new Lender("Alice", BigDecimal.TEN, 10),
                new Lender("Zav", BigDecimal.ZERO, 10));

        Loan result = LoanCalculator.calculateBestLoan(lenders, 25).get();

        assertEquals(25, result.getAmount());

        assertEquals(BigDecimal.ZERO, result.getComponents().get(0).getRight());
        assertEquals(Long.valueOf(10), result.getComponents().get(0).getLeft());

        assertEquals(BigDecimal.ONE.divide(new BigDecimal(12), MathContext.DECIMAL128), result.getComponents().get(1).getRight());
        assertEquals(Long.valueOf(10), result.getComponents().get(1).getLeft());

        assertEquals(BigDecimal.TEN.divide(new BigDecimal(12), MathContext.DECIMAL128), result.getComponents().get(2).getRight());
        assertEquals(Long.valueOf(5), result.getComponents().get(2).getLeft());

    }



}

package com.samcgardner.zopatest.calculator;

import com.samcgardner.zopatest.Parser;
import com.samcgardner.zopatest.model.Lender;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuoteCalculatorTest {

    @Test
    public void calculatesQuoteCorrectly() throws Exception {

        List<Lender> lenders = Parser.parseLenders("./src/test/resources/sample.csv");

        Loan loan = LoanCalculator.calculateBestLoan(lenders, 200L).get();


        Quote quote = QuoteCalculator.calculateQuote(loan);

        assertEquals(200L, quote.getRequestedAmount());

        MathContext roundingPrecision = new MathContext(6, RoundingMode.HALF_EVEN);
        assertEquals(new BigDecimal(0.00609167).round(roundingPrecision),
                quote.getRate().round(roundingPrecision));

        roundingPrecision = new MathContext(3, RoundingMode.HALF_EVEN);
        assertEquals(new BigDecimal(6.20).round(roundingPrecision), quote.getMonthlyRepayment().round(roundingPrecision));
        assertEquals(new BigDecimal(223.24).round(roundingPrecision), quote.getTotalRepayment().round(roundingPrecision));
    }

}
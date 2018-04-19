package com.samcgardner.zopatest;

import com.samcgardner.zopatest.calculator.LoanCalculator;
import com.samcgardner.zopatest.calculator.Quote;
import com.samcgardner.zopatest.calculator.QuoteCalculator;
import com.samcgardner.zopatest.model.Lender;
import com.samcgardner.zopatest.calculator.Loan;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        String inputCsv = null;
        String loanAmount = null;

        try {
            inputCsv = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Path to CSV must not be null!");
            System.exit(1);
        }

        try {
            loanAmount = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Loan amount must not be null!");
            System.exit(1);
        }

        List<Lender> lenders = null;

        try {
            lenders = Parser.parseLenders(inputCsv);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Could not parse lenders");
            System.exit(1);
        }

        Optional<Loan> loan = LoanCalculator.calculateBestLoan(lenders, Integer.parseInt(loanAmount));

        if(loan.isPresent()) {
            Quote quote = QuoteCalculator.calculateQuote(loan.get());
            System.out.println(quote.prettyPrint());
        } else {
            System.out.println("Could not calculate loan for this amount");
        }

    }
}

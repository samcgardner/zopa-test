package com.samcgardner.zopatest;

import com.samcgardner.zopatest.calculator.LoanCalculator;
import com.samcgardner.zopatest.model.Lender;
import com.samcgardner.zopatest.model.Loan;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Main {

    public Main() {
    }

    public static void main(String[] args) {

        String inputCsv = args[0];

        String loanAmount = args[1];

        if (inputCsv == null) {
            System.out.println("Path to CSV must not be null!");
            System.exit(1);
        }

        if (loanAmount == null) {
            System.out.println("Loan amount must not be null!");
            System.exit(1);
        }

        Parser parser = new Parser();
        List<Lender> lenders = null;
        try {
            lenders = parser.parseLenders(args[1]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Could not parse lenders");
            System.exit(1);
        }

        LoanCalculator calculator = new LoanCalculator();
        Optional<Loan> loan = calculator.calculateBestLoan(lenders);

        if(loan.isPresent()) {
            System.out.println(loan.get().prettyPrint());
        } else {
            System.out.println("Could not calculate loan for this amount");
        }

    }
}

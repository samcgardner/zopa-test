package com.samcgardner.zopatest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Lender {

    @JsonCreator
    public Lender(@JsonProperty("Lender") String name,
                  @JsonProperty("Rate") BigDecimal interestRate,
                  @JsonProperty("Available") long amountAvailable) {
        this.name = name;
        this.interestRate = interestRate;
        this.amountAvailable = amountAvailable;
    }

    @JsonProperty
    private final String name;
    @JsonProperty
    private final BigDecimal interestRate;
    @JsonProperty
    private final long amountAvailable;

    public String getName() {
        return name;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public long getAmountAvailable() {
        return amountAvailable;
    }
}

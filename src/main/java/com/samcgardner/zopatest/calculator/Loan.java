package com.samcgardner.zopatest.calculator;

import com.samcgardner.zopatest.util.Tuple;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Loan {

    private final long amount;
    private List<Tuple<Long, BigDecimal>> components;

    public Loan(long targetAmount) {
        this.amount = targetAmount;
        this.components = new LinkedList<>();
    }

    public long getAmount() {
        return amount;
    }

    public List<Tuple<Long, BigDecimal>> getComponents() {
        return components;
    }

    public void addComponent(Tuple<Long, BigDecimal> toAdd) {
        components.add(toAdd);
    }

}

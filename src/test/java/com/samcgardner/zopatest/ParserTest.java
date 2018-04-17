package com.samcgardner.zopatest;

import com.samcgardner.zopatest.model.Lender;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parsesEmptyCsvWithoutError() throws IOException {

        List<Lender> lenders = getLendersForPath("./src/test/resources/empty.csv");

        assertTrue(lenders.isEmpty());
    }

    @Test
    public void handlesHeadersWithoutError() throws IOException {

        List<Lender> lenders = getLendersForPath("./src/test/resources/emptyWithHeaders.csv");

        assertTrue(lenders.isEmpty());
    }

    @Test
    public void parsesACsv() throws IOException {

        List<Lender> lenders = getLendersForPath("./src/test/resources/sample.csv");

        Lender first = lenders.get(0);
        Lender second = lenders.get(1);

        assertEquals("Bob", first.getName());
        assertEquals(new BigDecimal("0.075"), first.getInterestRate());
        assertEquals(640L, first.getAmountAvailable());

        assertEquals("Jane", second.getName());
        assertEquals(new BigDecimal("0.069"), second.getInterestRate());
        assertEquals(480L, second.getAmountAvailable());
    }



    private List<Lender> getLendersForPath(String path) throws IOException {
        Parser parser = new Parser();

        return parser.parseLenders(path);
    }

}
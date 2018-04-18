package com.samcgardner.zopatest;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.samcgardner.zopatest.model.Lender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Parser {

    public static List<Lender> parseLenders(String lendersPath) throws IOException {

        try (InputStream stream = new FileInputStream(new File(lendersPath))) {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = buildSchema();
            MappingIterator<Lender> it = mapper
                    .readerFor(Lender.class)
                    .with(schema)
                    .readValues(stream);

            return it.readAll();
        }
    }

    private static CsvSchema buildSchema() {
        return CsvSchema.builder()
                .addColumn("Lender")
                .addColumn("Rate", CsvSchema.ColumnType.NUMBER)
                .addColumn("Available", CsvSchema.ColumnType.NUMBER)
                .build()
                .withHeader();

    }
}

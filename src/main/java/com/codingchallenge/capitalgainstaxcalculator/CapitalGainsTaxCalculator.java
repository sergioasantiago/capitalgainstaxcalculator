package com.codingchallenge.capitalgainstaxcalculator;

import com.codingchallenge.capitalgainstaxcalculator.domain.Operation;
import com.codingchallenge.capitalgainstaxcalculator.domain.Portfolio;
import com.codingchallenge.capitalgainstaxcalculator.domain.Tax;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CapitalGainsTaxCalculator {

    public static void main(String[] args) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    List<Operation> operations = parseJsonOperations(line);
                    List<Tax> taxes = calculateCapitalGainsTax(operations);
                    printAsJson(taxes);
                }
            }
        } catch (IOException e) {
            System.err.printf("Failed to read the input. %s%n", e);
            System.exit(1);
        }
    }

    private static List<Operation> parseJsonOperations(String jsonInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonInput, new TypeReference<>() {
        });
    }

    private static void printAsJson(List<Tax> taxes) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(taxes));
    }

    /**
     * Calculate the taxes that need to be paid based on the list of consecutive operations
     *
     * @param operations List of operations
     * @return List of taxes based on the provided operations
     */
    private static List<Tax> calculateCapitalGainsTax(List<Operation> operations) {
        List<Tax> taxes = new ArrayList<>();
        Portfolio portfolio = new Portfolio();

        for (Operation operation : operations) {
            Tax tax = portfolio.calculateTaxes(operation);
            taxes.add(tax);
        }

        return taxes;
    }
}

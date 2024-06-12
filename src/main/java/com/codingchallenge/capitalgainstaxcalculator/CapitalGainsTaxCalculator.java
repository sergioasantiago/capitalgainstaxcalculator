package com.codingchallenge.capitalgainstaxcalculator;

import com.codingchallenge.capitalgainstaxcalculator.domain.Operation;
import com.codingchallenge.capitalgainstaxcalculator.domain.OperationResult;
import com.codingchallenge.capitalgainstaxcalculator.domain.Portfolio;
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
                    List<OperationResult> operationResults = calculateCapitalGainsTax(operations);
                    printAsJson(operationResults);
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

    private static void printAsJson(List<OperationResult> taxes) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(taxes));
    }

    /**
     * Calculate the taxes that need to be paid based on the list of consecutive operations
     *
     * @param operations List of operations
     * @return List of taxes based on the provided operations
     */
    private static List<OperationResult> calculateCapitalGainsTax(List<Operation> operations) {
        List<OperationResult> operationResults = new ArrayList<>();
        Portfolio portfolio = new Portfolio();

        for (Operation operation : operations) {
            OperationResult operationResult = portfolio.performOperation(operation);
            operationResults.add(operationResult);
        }

        return operationResults;
    }
}

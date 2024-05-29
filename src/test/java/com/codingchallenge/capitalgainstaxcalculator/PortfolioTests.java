package com.codingchallenge.capitalgainstaxcalculator;

import com.codingchallenge.capitalgainstaxcalculator.domain.Operation;
import com.codingchallenge.capitalgainstaxcalculator.domain.Portfolio;
import com.codingchallenge.capitalgainstaxcalculator.domain.Tax;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortfolioTests {

    @Test
    void testBuyNoTaxesApplied() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 100)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testSellWithLossNoTaxesApplied() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 100),
                new Operation("sell", 5.00, 100)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testRoundingValue() {
        List<Operation> operations = List.of(
                new Operation("buy", 20.00, 10000),
                new Operation("buy", 10.00, 5000),
                new Operation("sell", 16.67, 15000)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testSellValueLessThanMinTaxable() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 100),
                new Operation("sell", 15.00, 50),
                new Operation("sell", 15.00, 50)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testLossNoTaxesAppliedAfterProfit() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 20.00, 5000),
                new Operation("sell", 5.00, 5000)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(10000),
                new Tax(0.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testMultipleOperationsNotAffectingEachOther() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 100),
                new Operation("sell", 15.00, 50),
                new Operation("sell", 15.00, 50)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0)
        );

        Portfolio portfolio = new Portfolio();

        runTest(portfolio, operations, expectedTaxes);

        operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 20.00, 5000),
                new Operation("sell", 5.00, 5000)
        );

        expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(10000),
                new Tax(0.0)
        );

        runTest(portfolio, operations, expectedTaxes);
    }

    @Test
    void testApplyTaxesWhenNetProfitAndTransactionAmountGreaterThanMinTaxable() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 5.00, 5000),
                new Operation("sell", 20.00, 3000)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(1000.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testSellWithoutLossNorProfit() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("buy", 25.00, 5000),
                new Operation("sell", 15.00, 10000)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testConsecutiveSellOperationWithAndWithoutProfit() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("buy", 25.00, 5000),
                new Operation("sell", 15.00, 10000),
                new Operation("sell", 25.00, 5000)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0),
                new Tax(10000.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testLossDeductedAfterProfitSellTransactions() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 2.00, 5000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 25.00, 1000)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0),
                new Tax(3000.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testNewOperationsAfterAllSharesSold() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 2.00, 5000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 25.00, 1000),
                new Operation("buy", 20.00, 10000),
                new Operation("sell", 15.00, 5000),
                new Operation("sell", 30.00, 4350),
                new Operation("sell", 30.00, 650)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0),
                new Tax(0.0),
                new Tax(3000.0),
                new Tax(0.0),
                new Tax(0.0),
                new Tax(3700.0),
                new Tax(0.0)
        );

        runTest(operations, expectedTaxes);
    }

    @Test
    void testAlternatingOperationsWithProfit() {
        List<Operation> operations = List.of(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 50.00, 10000),
                new Operation("buy", 20.00, 10000),
                new Operation("sell", 50.00, 10000)
        );

        List<Tax> expectedTaxes = List.of(
                new Tax(0.0),
                new Tax(80000.0),
                new Tax(0.0),
                new Tax(60000.0)
        );

        runTest(operations, expectedTaxes);
    }

    void runTest(List<Operation> operations, List<Tax> expectedTaxes) {
        Portfolio portfolio = new Portfolio();
        runTest(portfolio, operations, expectedTaxes);
    }

    void runTest(Portfolio portfolio, List<Operation> operations, List<Tax> expectedTaxes) {
        List<Tax> taxes = new ArrayList<>();

        for (Operation operation : operations) {
            taxes.add(portfolio.calculateTaxes(operation));
        }

        assertEquals(expectedTaxes, taxes);
    }
}

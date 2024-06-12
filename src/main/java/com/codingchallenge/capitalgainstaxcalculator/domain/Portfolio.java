package com.codingchallenge.capitalgainstaxcalculator.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Portfolio {

    private static final double TAX_RATE = 0.2;

    private static final double TAXABLE_MIN_VALUE = 20000;

    private int totalShares = 0;

    private double profit = 0.0;

    private BigDecimal averagePrice = BigDecimal.valueOf(0.0).setScale(2, RoundingMode.HALF_UP);


    /**
     * Calculates the capital gains tax for a given operation based on the portfolio's current state.
     * - If it's a "buy" operation, no tax is applied.
     * - If it's a "sell" operation, if there's a net profit from the transaction considering the combined effect
     * of previous buy and sell operations and the total value transaction exceeds the {@value TAXABLE_MIN_VALUE},
     * The tax rate of {@link #TAX_RATE} is applied to the taxable net profit, after deducting the previous losses,
     * to calculate the capital gains tax.
     *
     * @param operation the Operation object representing the transaction
     * @return a Tax object containing the calculated capital gains tax (0 if not applicable)
     */
    public OperationResult performOperation(Operation operation) {
        if (operation.getOperation().equals("buy")) {
            return buy(operation);
        } else if (operation.getOperation().equals("sell")) {
            return sell(operation);
        }

        OperationResult operationResult = new OperationResult();
        operationResult.setError(OperationResultError.INVALID_OPERATION);
        return operationResult;
    }

    /**
     * Updates the portfolio's average price and total shares based on a buy operation.
     *
     * @param operation the Operation object representing the buy transaction
     */
    private OperationResult buy(Operation operation) {
        double doubleAveragePrice = calculateAveragePrice(operation.getQuantity(), operation.getUnitCost());
        averagePrice = BigDecimal.valueOf(doubleAveragePrice).setScale(2, RoundingMode.HALF_UP);
        totalShares += operation.getQuantity();
        return new OperationResult();
    }

    /**
     * Updates the portfolio's total shares and calculates potential capital gains tax for a sell operation.
     *
     * @param operation the Operation object representing the sell transaction
     * @return the amount of capital gains tax calculated for the sell operation (0 if not taxable)
     */
    private OperationResult sell(Operation operation) {
        OperationResult operationResult = new OperationResult();

        if (totalShares < operation.getQuantity()) {
            operationResult.setError(OperationResultError.MAX_STOCKS);
            return operationResult;
        }

        double taxes = 0;

        totalShares -= operation.getQuantity();
        double grossProfit = calculateGrossProfit(operation.getQuantity(), operation.getUnitCost());
        profit += grossProfit;

        if (profit > 0) {
            double netProfit = profit;
            profit = 0;

            if (taxable(operation.getQuantity(), operation.getUnitCost())) {
                taxes = performOperation(netProfit);
            }
        }

        operationResult.setTax(new Tax(taxes));
        return operationResult;
    }

    private double performOperation(double netProfit) {
        return netProfit * TAX_RATE;
    }

    private double calculateAveragePrice(double quantity, double cost) {
        return ((totalShares * averagePrice.doubleValue()) + (quantity * cost)) / (totalShares + quantity);
    }

    private double calculateGrossProfit(int quantity, double unitCost) {
        return (unitCost - averagePrice.doubleValue()) * quantity;
    }

    private boolean taxable(int quantity, double unitCost) {
        return (quantity * unitCost) > TAXABLE_MIN_VALUE;
    }
}

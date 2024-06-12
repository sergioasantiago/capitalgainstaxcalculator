package com.codingchallenge.capitalgainstaxcalculator.domain;

public class OperationResultError {
    static final OperationResultError MAX_STOCKS = new OperationResultError("Can't sell more stocks than you have");

    static final OperationResultError INVALID_OPERATION = new OperationResultError("Invalid operation");

    final String error;

    private OperationResultError(String error) {
        this.error = error;
    }
}

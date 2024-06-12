package com.codingchallenge.capitalgainstaxcalculator.domain;

import lombok.Data;

@Data
public class OperationResult {
    private Tax tax = null;

    private OperationResultError error = null;

    public OperationResult() {
    }

    public OperationResult(Tax tax) {
        this.tax = tax;
    }
}

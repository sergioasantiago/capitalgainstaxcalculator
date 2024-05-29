package com.codingchallenge.capitalgainstaxcalculator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    private String operation;

    @JsonProperty(value = "unit-cost")
    private double unitCost;

    private int quantity;
}

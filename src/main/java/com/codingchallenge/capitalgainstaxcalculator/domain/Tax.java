package com.codingchallenge.capitalgainstaxcalculator.domain;


import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Tax {
    
    final BigDecimal tax;

    public Tax(double tax) {
        this.tax = BigDecimal.valueOf(tax).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return tax.toString();
    }
}

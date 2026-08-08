package org.example.pricefeed;

import java.math.BigDecimal;

public class PriceGenerator {

    public static BigDecimal generatePrice(BigDecimal currentPrice){
        double changePercent = (Math.random() - 0.5) * 0.02;
        return currentPrice.multiply(BigDecimal.valueOf(1 + changePercent));
    }

}

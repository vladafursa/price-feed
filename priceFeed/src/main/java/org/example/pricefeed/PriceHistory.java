package org.example.pricefeed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class PriceHistory {
    private ConcurrentHashMap<FinancialInstrument, ArrayList<BigDecimal>> history;

    public PriceHistory() {
        this.history = new ConcurrentHashMap<>();
    }

    public void addPrice(FinancialInstrument instrument, BigDecimal newPrice){
        if (instrument == null || newPrice == null) {
            throw new IllegalArgumentException();
        }
        history.computeIfAbsent(instrument, k -> new ArrayList<>())
                .add(newPrice);
    }

    public BigDecimal getLastPrice(FinancialInstrument instrument){
        ArrayList<BigDecimal> prices = history.get(instrument);
        if(prices.isEmpty()){
            return null;
        }
        return prices.getLast();
    }

    public BigDecimal getPreviousPrice(FinancialInstrument instrument){
        ArrayList<BigDecimal> prices = history.get(instrument);
        if(prices.isEmpty() || prices.size()<2){
            return null;
        }
        return prices.get(prices.size()-2);
    }

    public ArrayList<BigDecimal> getHistory(FinancialInstrument instrument){
        if (history.containsKey(instrument)) {
            ArrayList<BigDecimal> prices = history.get(instrument);
            if (prices != null && !prices.isEmpty()) {
                return new ArrayList<>(prices);
            }
        }
        return new ArrayList<>();
    }

}

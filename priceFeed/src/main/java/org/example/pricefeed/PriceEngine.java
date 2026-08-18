package org.example.pricefeed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class PriceEngine {
    ConcurrentHashMap<String, FinancialInstrument> instruments;
    PriceHistory history;

    public PriceEngine(PriceHistory history, ConcurrentHashMap<String, FinancialInstrument> instruments) {
        this.history = history;
        this.instruments = instruments;
    }

    public void updateSpecificPrice(String symbol){
        FinancialInstrument instrument = instruments.get(symbol);
        if (instrument != null) {
            updatePrice(instrument);
        }
    }

    public void updateAllPrices(){
        for(FinancialInstrument instrument : instruments.values()){
            updatePrice(instrument);
        }
    }

    public void updatePrice(FinancialInstrument instrument){
        BigDecimal newPrice = PriceGenerator.generatePrice(instrument.getPrice());
        instrument.setPrice(newPrice);
        instrument.setLastUpdate(LocalDateTime.now());
        history.addPrice(instrument, newPrice);
    }

    public void addInstrument(FinancialInstrument instrument){
        instruments.put(instrument.getName(), instrument);
    }

    public FinancialInstrument getInstrument(String symbol) {
        return instruments.get(symbol);
    }

}

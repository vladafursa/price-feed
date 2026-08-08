package org.example.pricefeed;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinancialInstrument {
    private String name;
    private BigDecimal price;
    private LocalDateTime lastUpdate;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public LocalDateTime getLastUpdate(){
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    FinancialInstrument(String name){
        this.name = name;
    }

    FinancialInstrument(String name, BigDecimal price, LocalDateTime lastUpdate) {
        this.name = name;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }
}

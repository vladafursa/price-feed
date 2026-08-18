package org.example.pricefeed;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;

public class ChartDataService {
    private static final double MARGIN = 0.05;
    private static final int VISIBLE_POINTS = 10;

    private final LineChart<Number, Number> lineChart;
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();

    private double minPrice = Double.MAX_VALUE;
    private double maxPrice = Double.MIN_VALUE;

    public ChartDataService(LineChart<Number, Number> lineChart, NumberAxis xAxis, NumberAxis yAxis) {
        this.lineChart = lineChart;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public void configure() {
        xAxis.setLabel("Time");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(VISIBLE_POINTS);
        xAxis.setTickUnit(1);

        yAxis.setLabel("Price");
        yAxis.setAutoRanging(true);
        yAxis.setTickUnit(25);

        lineChart.setTitle("BTC/USD");
        lineChart.setLegendVisible(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(false);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);

        lineChart.getData().add(series);
    }

    public void addPoint(int time, BigDecimal price) {
        double priceValue = price.doubleValue();

        series.getData().add(new XYChart.Data<>(time, price));

        if (priceValue < minPrice) minPrice = priceValue;
        if (priceValue > maxPrice) maxPrice = priceValue;

        updateXAxisWindow(time);
        updateYAxisRange();
    }

    private void updateXAxisWindow(int time) {
        if (time > VISIBLE_POINTS) {
            xAxis.setLowerBound(time - (VISIBLE_POINTS - 1));
            xAxis.setUpperBound(time + 1);
        }
    }

    private void updateYAxisRange() {
        double range = maxPrice - minPrice;
        if (range <= 0) {
            return;
        }

        double lower = Math.floor((minPrice - range * MARGIN) / 100) * 100;
        double upper = Math.ceil((maxPrice + range * MARGIN) / 100) * 100;

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(lower);
        yAxis.setUpperBound(upper);
        yAxis.setTickUnit((upper - lower) / VISIBLE_POINTS);
    }
}
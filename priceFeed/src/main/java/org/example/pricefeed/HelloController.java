package org.example.pricefeed;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private double minPrice = Double.MAX_VALUE;
    private double maxPrice = Double.MIN_VALUE;
    private static final double MARGIN = 0.05;

    private XYChart.Series<Number, Number> series;

    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        xAxis.setLabel("Time");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(10);
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

        series = new XYChart.Series<>();

        lineChart.getData().add(series);
    }

    public void updateChart(int time, BigDecimal price) {
        Platform.runLater(() -> {
            double priceValue = price.doubleValue();

            series.getData().add(new XYChart.Data<>(time, price));

            if (priceValue < minPrice) minPrice = priceValue;
            if (priceValue > maxPrice) maxPrice = priceValue;


            if (time > 10) {
                xAxis.setLowerBound(time - 9);
                xAxis.setUpperBound(time + 1);
            }

            if (maxPrice - minPrice > 0) {
                double range = maxPrice - minPrice;
                double lower = minPrice - range * MARGIN;
                double upper = maxPrice + range * MARGIN;

                lower = Math.floor(lower / 100) * 100;
                upper = Math.ceil(upper / 100) * 100;

                yAxis.setAutoRanging(false);
                yAxis.setLowerBound(lower);
                yAxis.setUpperBound(upper);
                yAxis.setTickUnit((upper - lower) / 10);
            }
        });
    }
}
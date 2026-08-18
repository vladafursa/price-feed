package org.example.pricefeed;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;

    private ChartDataService chartView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chartView = new ChartDataService(lineChart, xAxis, yAxis);
        chartView.configure();
    }


    public void updateChart(int time, BigDecimal price) {
        Platform.runLater(() -> chartView.addPoint(time, price));
    }
}
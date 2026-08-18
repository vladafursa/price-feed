package org.example.pricefeed;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class HelloApplication extends Application {
    private volatile boolean running = true;
    @Override
    public void start(Stage stage) throws IOException {

        PriceHistory history = new PriceHistory();

        ConcurrentHashMap<String, FinancialInstrument> instruments =
                new ConcurrentHashMap<>();

        PriceEngine priceEngine =
                new PriceEngine(history, instruments);

        FinancialInstrument btc = new FinancialInstrument(
                "BTC/USD",
                new BigDecimal("118500"),
                LocalDateTime.now()
        );

        priceEngine.addInstrument(btc);

        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        HelloApplication.class.getResource("hello-view.fxml")
                );

        Scene scene = new Scene(fxmlLoader.load());

        HelloController controller = fxmlLoader.getController();


        stage.setTitle("Price chart");
        stage.setScene(scene);
        stage.show();

        Thread priceThread = new Thread(() -> {
            int time = 0;
            while (running) {
                priceEngine.updateAllPrices();
                BigDecimal price = btc.getPrice();

                controller.updateChart(++time, price);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        priceThread.setName("PriceGenerator");
        priceThread.start();

        stage.setOnCloseRequest(event -> {
            running = false;
            if (priceThread != null) {
                priceThread.interrupt();
                try {
                    priceThread.join(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Platform.exit();
        });
    }
}
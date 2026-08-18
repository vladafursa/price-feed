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

    private static final String BTC_SYMBOL = "BTC/USD";
    private static final long UPDATE_INTERVAL_MS = 100;
    private static final long SHUTDOWN_JOIN_TIMEOUT_MS = 1000;


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

        Thread priceThread = startPriceThread(priceEngine, btc, controller);
        setupGracefulShutdown(stage, priceThread);
    }

    private Thread startPriceThread(PriceEngine priceEngine, FinancialInstrument btc, HelloController controller) {
        Thread priceThread = new Thread(() -> runPriceLoop(priceEngine, btc, controller));
        priceThread.setName("PriceGenerator");
        priceThread.start();
        return priceThread;
    }

    private void runPriceLoop(PriceEngine priceEngine, FinancialInstrument btc, HelloController controller) {
        int time = 0;
        while (running) {
            priceEngine.updateAllPrices();
            BigDecimal price = btc.getPrice();

            controller.updateChart(++time, price);

            try {
                Thread.sleep(UPDATE_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void setupGracefulShutdown(Stage stage, Thread priceThread) {
        stage.setOnCloseRequest(event -> {
            running = false;
            priceThread.interrupt();
            try {
                priceThread.join(SHUTDOWN_JOIN_TIMEOUT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Platform.exit();
        });
    }
}
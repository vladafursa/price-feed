# Real-Time Price Feed Simulator

A small JavaFX desktop app that simulates a live price feed for a financial instrument (BTC/USD by default) and renders it as a real-time line chart. The goal of this project was to revisit Java concurrency concepts while building a simple desktop application.

## Features

- Simulated price generation on a background thread
- Thread-safe instrument storage 
- Graceful shutdown
- Real-time scrolling
- Layered design separating business logic from presentation


## Requirements

- JDK 25
- JavaFX SDK
- Maven

## Project structure

```
├── Analytics.java # preparation for future analytics
├── ChartDataService.java # UI logic
├── FinancialInstrument.java # instrument model
├── HelloApplication.java # entry point: wires up the engine, loads the scene, manages the price thread lifecycle
├── HelloController.java # FXML controller — bridges background-thread updates onto the FX Application Thread
├── PriceEngine.java # main business logic: holds instruments, applies price updates, records history
├── PriceGenerator.java #  generator of the next simulated price
└── PriceHistory.java # historical price data storage per instrument
```


## Class diagram
<img width="889" height="516" alt="Screenshot 2026-08-19 at 00 02 21" src="https://github.com/user-attachments/assets/3e3ad7ca-cc98-4f37-a1e6-469ca932ab22" />

## Future improvements

- Multiple financial instruments
- Real-time analytics
- Usage of history
- Real data instead of randomly generated

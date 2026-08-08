module org.example.pricefeed {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.pricefeed to javafx.fxml;
    exports org.example.pricefeed;
}
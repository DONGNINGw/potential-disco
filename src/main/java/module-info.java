module dongning.potentialdisco {
    requires javafx.controls;
    requires javafx.fxml;


    opens dongning.potentialdisco to javafx.fxml;
    exports dongning.potentialdisco;
}
module dongning.potentialdisco {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens dongning.potentialdisco to javafx.fxml;
    exports dongning.potentialdisco;
}
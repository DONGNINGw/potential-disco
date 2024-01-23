package dongning.potentialdisco;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CMenuBar extends VBox {
    public CMenuBar(){
        super();

        Button fileButton = new Button("File");
        Button helpButton = new Button("Help");

        this.getChildren().addAll(fileButton, helpButton);
    }
}

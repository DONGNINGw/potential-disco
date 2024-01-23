package dongning.potentialdisco;

import dongning.todowin.CMenuBar;
import javafx.scene.layout.BorderPane;

public class MainWindow extends BorderPane {
    public MainWindow(){
        //Create a menu and place it on the left side
        CMenuBar cMenuBar = new CMenuBar();// Assuming the MenuBar class has been defined
// Add menu items to menuBar...
        this.setLeft(cMenuBar);// Set the menu bar to the left panel
    }
}

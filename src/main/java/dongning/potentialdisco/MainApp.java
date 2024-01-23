package dongning.potentialdisco;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle(" Todo List");

        MainWindow rootLayout = new MainWindow();
        Scene scene = new Scene(rootLayout, 800,800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}

package jason.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/** Provides the JavaFX application entry point for Jason's optional GUI. */
public class GuiMain extends Application {
    /** Displays the initial JavaFX window. */
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello from Jason!");
        Scene scene = new Scene(helloWorld);
        stage.setScene(scene);
        stage.setTitle("Jason");
        stage.show();
    }
}

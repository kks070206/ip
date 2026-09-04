package jason.gui;

import java.io.IOException;

import jason.Jason;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** Provides the JavaFX application entry point for Jason's optional GUI. */
public class GuiMain extends Application {
    /**
     * Loads the main GUI view and displays it in the primary stage.
     *
     * @param stage primary JavaFX stage provided by the runtime.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GuiMain.class.getResource("/view/MainWindow.fxml"));
            AnchorPane mainWindow = fxmlLoader.load();
            fxmlLoader.<MainWindow>getController().setJason(new Jason());

            Scene scene = new Scene(mainWindow);
            stage.setScene(scene);
            stage.setTitle("Jason");
            stage.show();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load the Jason GUI.", exception);
        }
    }
}

package jason.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Provides the JavaFX application entry point for Jason's optional GUI. */
public class GuiMain extends Application {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;

    /**
     * Displays the initial JavaFX window and its chatbot layout.
     *
     * @param stage primary JavaFX stage provided by the runtime.
     */
    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        Button sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(scrollPane, 43.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(userInput, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setRightAnchor(userInput, 76.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        dialogContainer.getChildren().add(DialogBox.getJasonDialog("How may I help you today?"));
        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

        Scene scene = new Scene(mainLayout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Jason");
        stage.show();
    }

    /**
     * Adds the user's input to the conversation and clears the input field.
     * Empty input is ignored so that blank dialog boxes are not displayed.
     */
    private void handleUserInput() {
        String message = userInput.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(message));
        userInput.clear();
    }
}

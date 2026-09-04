package jason.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** Represents one chatbot message with a simple speaker avatar. */
public class DialogBox extends HBox {
    private static final String USER_AVATAR = "🙂";
    private static final String JASON_AVATAR = "🤖";

    @FXML
    private Label text;
    @FXML
    private Label avatar;

    /**
     * Creates a dialog box from its FXML view.
     *
     * @param message message to display.
     * @param avatarText speaker marker to display.
     */
    private DialogBox(String message, String avatarText) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load the dialog box GUI.", exception);
        }

        text.setText(message);
        avatar.setText(avatarText);
        getStyleClass().add("dialog-box");
        text.getStyleClass().add("dialog-text");
        avatar.getStyleClass().add("avatar");
        getStylesheets().add(DialogBox.class.getResource("/css/dialog-box.css").toExternalForm());
    }

    /**
     * Creates a dialog box representing a user message.
     *
     * @param message message written by the user.
     * @return a right-aligned user dialog box.
     */
    public static DialogBox getUserDialog(String message) {
        DialogBox dialogBox = new DialogBox(message, USER_AVATAR);
        dialogBox.getStyleClass().add("user-dialog");
        return dialogBox;
    }

    /**
     * Creates a dialog box representing a Jason response.
     *
     * @param message response written by Jason.
     * @return a left-aligned Jason dialog box.
     */
    public static DialogBox getJasonDialog(String message) {
        DialogBox dialogBox = new DialogBox(message, JASON_AVATAR);
        dialogBox.flip();
        dialogBox.getStyleClass().add("jason-dialog");
        return dialogBox;
    }

    /** Places the avatar on the left to distinguish Jason's responses. */
    private void flip() {
        getChildren().setAll(avatar, text);
        setAlignment(Pos.TOP_LEFT);
    }
}

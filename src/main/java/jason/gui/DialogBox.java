package jason.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** Represents one chatbot message with a simple speaker avatar. */
public class DialogBox extends HBox {
    private static final String USER_AVATAR = "🙂";
    private static final String JASON_AVATAR = "🤖";

    private final Label text;
    private final Label avatar;

    private DialogBox(String message, String avatarText) {
        text = new Label(message);
        avatar = new Label(avatarText);
        text.setWrapText(true);
        setSpacing(10);
        setAlignment(Pos.TOP_RIGHT);
        getChildren().addAll(text, avatar);
    }

    /**
     * Creates a dialog box representing a user message.
     *
     * @param message message written by the user.
     * @return a right-aligned user dialog box.
     */
    public static DialogBox getUserDialog(String message) {
        return new DialogBox(message, USER_AVATAR);
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
        return dialogBox;
    }

    /** Places the avatar on the left to distinguish Jason's responses. */
    private void flip() {
        getChildren().setAll(avatar, text);
        setAlignment(Pos.TOP_LEFT);
    }
}

package brobot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public final class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final BroBot broBot;

    public static final Image USER_IMAGE = new Image(MainWindow.class.getResourceAsStream("/images/DaUser.png"));
    public static final Image BRO_BOT_IMAGE = new Image(MainWindow.class.getResourceAsStream("/images/BroBot Mascot.png"));

    public MainWindow() {
        broBot = BroBot.getSingleton();
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
            DialogBox.getBroBotDialog("Hello, I'm BroBot! What can I do for you?"),
            DialogBox.getBroBotDialog(broBot.getLoadMessage())
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing BroBot's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = broBot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getBroBotDialog(response)
        );
        userInput.clear();
    }
}

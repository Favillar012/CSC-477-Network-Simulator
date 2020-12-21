package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class builds the scene for the Message Prompt dialog box.
 * Date Modified: 9/15/2020
 * @author Fernando Villarreal
 */
public class MessagePrompt {

    //============================= CLASS VARIABLES =============================

    // Instance Variables
    private final VBox vbox;
    private final Label label;
    private final TextField textField;
    private final Button button;
    private final Stage stage;
    private final Scene nextScene;
    private final char messageToInput;
    // Static Variables
    private static final char inputA = 'A';
    private static final char inputB = 'B';
    private static CharSequence messageA;
    private static CharSequence messageB;

    //============================= CONSTRUCTOR =============================

    public MessagePrompt(Stage stage, Scene nextScene, char messageToInput) {
        // Initialize the objects for this pane
        this.stage = stage;
        this.nextScene = nextScene;
        this.vbox = new VBox();
        this.label = new Label("Enter message " + messageToInput + ":");
        this.textField = new TextField();
        this.button = new Button("Ok");
        this.messageToInput = messageToInput;
        // Set the action for the button
        this.button.setOnAction(e -> this.loadNextScene());
        // Set up the VBox
        this.vbox.getChildren().addAll(this.label, this.textField, this.button);
        this.vbox.setPadding(new Insets(10, 10, 10, 10));
        this.vbox.setSpacing(10);
    }

    //============================= METHODS =============================

    /**
     * Load the next scene if the message is valid.
     */
    public void loadNextScene() {
        CharSequence chars = this.textField.getCharacters();
        if (chars.length() <= 0) {
            MessagePromptAlert.display("The message cannot be empty.");
        } else {
            if (this.messageToInput == MessagePrompt.inputA) {
                MessagePrompt.messageA = chars;
            } else if (this.messageToInput == MessagePrompt.inputB){
                MessagePrompt.messageB = chars;
            } else {
                MessagePrompt.messageA = chars;
                MessagePrompt.messageB = chars;
            }
            this.stage.setScene(this.nextScene);
            this.stage.show();
        }
    }

    /**
     * Return the VBox pane of this class.
     * @return VBox
     */
    public VBox getPane() {
        return this.vbox;
    }

    /**
     * Returns messageA.
     * @return CharSequence
     */
    public static CharSequence getMessageA() {
        return MessagePrompt.messageA;
    }

    /**
     * Returns message B.
     * @return
     */
    public static CharSequence getMessageB() { return MessagePrompt.messageB; }
}

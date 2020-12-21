package GUI.LayerContentsWindows;

import Network.Datagrams.Datagram;
import Network.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class creates a window to display the message contents.
 * Date Modified: 9/18/2020
 * @author Fernando Villarreal
 */
public class MessageContents {

    public static void display(Datagram datagram) {
        Message message = datagram.getMessage();
        // Set up the MessageContents window
        Stage messageBox = new Stage();
        messageBox.initModality(Modality.APPLICATION_MODAL);
        messageBox.setTitle("Message Contents");
        // Create objects for the scene
        VBox vbox = new VBox();
        Label label1 = new Label("Original Message: " + message.toString());
        Button button = new Button("Ok");
        button.setOnAction(e -> messageBox.close());
        // Adjust the VBox
        vbox.getChildren().addAll(label1, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER_LEFT);
        // Set up the scene and show the alertBox window
        Scene scene = new Scene(vbox, 350, 150);
        messageBox.setScene(scene);
        messageBox.showAndWait();
    }
}

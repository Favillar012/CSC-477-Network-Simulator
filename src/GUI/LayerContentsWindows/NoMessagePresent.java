package GUI.LayerContentsWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class displays a "Message has not arrived here" message in a window.
 * Date Modified: 9/18/2020
 * @author Fernando Villarreal
 */
public class NoMessagePresent {

    public static void display() {
        // Set up the alertBox window
        Stage alertBox = new Stage();
        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle("No Message Present");
        // Create objects for the scene
        VBox vbox = new VBox();
        Label label = new Label("The message has not arrived here yet.");
        Button button = new Button("Ok");
        button.setOnAction(e -> alertBox.close());
        // Adjust the VBox
        vbox.getChildren().addAll(label, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        // Set up the scene and show the alertBox window
        Scene scene = new Scene(vbox, 250, 150);
        alertBox.setScene(scene);
        alertBox.showAndWait();
    }
}

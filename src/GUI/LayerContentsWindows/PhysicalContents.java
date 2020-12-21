package GUI.LayerContentsWindows;

import Network.Datagrams.EthernetFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class creates a separate window to display the message contents at the Physical Layer.
 * @author Fernando Villarreal
 * @date 11/20/2020
 */
public class PhysicalContents {

    public static void display(EthernetFrame ethernetFrame) {
        // Set up the PhysicalContents window
        Stage physicalBox = new Stage();
        physicalBox.initModality(Modality.APPLICATION_MODAL);
        physicalBox.setTitle("Physical Layer: Bit Sequence");
        // Create objects for the scene
        VBox vbox = new VBox();
        Label label1 = new Label("Bit Sequence of the Frame");
        Label label2 = new Label(ethernetFrame.getBinaryRepresentation());
        label2.setWrapText(true);
        Button button = new Button("Ok");
        button.setOnAction(e -> physicalBox.close());
        // Adjust the VBox
        vbox.getChildren().addAll(label1, label2, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER_LEFT);
        // Set up the scene nd show the window
        Scene scene = new Scene(vbox, 600, 250);
        physicalBox.setScene(scene);
        physicalBox.showAndWait();
    }
}

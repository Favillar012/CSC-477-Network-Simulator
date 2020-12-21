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
 * This class creates a separate window to display the message contents at the Data-Link Layer.
 * @author Fernando Villarreal
 * @date 11/17/2020
 */
public class DataLinkContents {

    public static void display(EthernetFrame ethernetFrame) {
        // Set up the DataLinkContents window
        Stage dataLinkBox = new Stage();
        dataLinkBox.initModality(Modality.APPLICATION_MODAL);
        dataLinkBox.setTitle("Data-Link Layer: Message Contents");
        // Create objects for the scene
        VBox vbox = new VBox();
        Label label1 = new Label("Original Message: " + ethernetFrame.getMessage().toString());
        Label label2 = new Label("Destination Link Address: " + ethernetFrame.getDestinationLink());
        Label label3 = new Label("Source Link Address: " + ethernetFrame.getSourceLink());
        Label label4 = new Label("Type: " + ethernetFrame.getType());
        Label label5 = new Label("CRC: " + ethernetFrame.getCrc());
        Button button = new Button("Ok");
        button.setOnAction(e -> dataLinkBox.close());
        // Adjust the VBox
        vbox.getChildren().addAll(label1, label2, label3, label4, label5, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER_LEFT);
        // Set up the scene nd show the window
        Scene scene = new Scene(vbox, 400, 250);
        dataLinkBox.setScene(scene);
        dataLinkBox.showAndWait();
    }
}

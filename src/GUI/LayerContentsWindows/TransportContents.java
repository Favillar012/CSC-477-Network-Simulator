package GUI.LayerContentsWindows;

import Network.Datagrams.UserDatagram;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class creates a window to display the message contents at the Transport Layer.
 * Date Modified: 10/15/2020
 * @author Fernando Villarreal
 */
public class TransportContents {

    public static void display(UserDatagram userDatagram) {
        // Set up the TransportContents window
        Stage transportBox = new Stage();
        transportBox.initModality(Modality.APPLICATION_MODAL);
        transportBox.setTitle("Transport Layer: Message Contents");
        // Create objects for the scene
        VBox vbox = new VBox();
        Label label1 = new Label("Original Message: " + userDatagram.getMessage().toString());
        Label label2 = new Label("Encoded Message: " + userDatagram.getMessage().getHexMessage());
        Label label3 = new Label("UDP Header: " + userDatagram.getUdpHeader());
        Label label4 = new Label("Checksum: " + userDatagram.getTransChecksum());
        Button button = new Button("Ok");
        button.setOnAction(e -> transportBox.close());
        // Adjust the VBox
        vbox.getChildren().addAll(label1, label2, label3, label4, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER_LEFT);
        // Set up the scene and show the alertBox window
        Scene scene = new Scene(vbox, 550, 300);
        transportBox.setScene(scene);
        transportBox.showAndWait();
    }
}

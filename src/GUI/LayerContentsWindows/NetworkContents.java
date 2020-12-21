package GUI.LayerContentsWindows;

import Network.Datagrams.IPDatagram;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class creates a separate window to display the message contents at the Network Layer.
 * @author Fernando Villarreal
 * @date 10/24/2020
 */
public class NetworkContents {

    public static void display(IPDatagram ipDatagram) {
        // Set up the NetworkContents window
        Stage networkBox = new Stage();
        networkBox.initModality(Modality.APPLICATION_MODAL);
        networkBox.setTitle("Network Layer: Message Contents");
        // Create objects for the scene
        VBox vbox = new VBox();
        Label label1 = new Label("Original Message: " + ipDatagram.getMessage().toString());
        Label label2 = new Label("Header Length: " + ipDatagram.getIPHeaderLength());
        Label label3 = new Label("Total Length: " + ipDatagram.getIpTotalLength());
        Label label4 = new Label("Identification: " + ipDatagram.getIdentification());
        Label label5 = new Label("Flags: " + ipDatagram.getFlags());
        Label label6 = new Label("Frag Offset: " + ipDatagram.getFragOffset());
        Label label7 = new Label("Header Checksum: " + ipDatagram.getNetChecksum());
        Button button = new Button("Ok");
        button.setOnAction(e -> networkBox.close());
        // Adjust the VBox
        vbox.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER_LEFT);
        // Set up the scene and show the alertBox window
        Scene scene = new Scene(vbox, 550, 300);
        networkBox.setScene(scene);
        networkBox.showAndWait();
    }
}

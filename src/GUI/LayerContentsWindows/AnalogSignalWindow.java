package GUI.LayerContentsWindows;

import Network.Datagrams.EthernetFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This class draws and displays the analog signal for the message as a sine wave using
 * Binary ASK.
 * @author Fernando Villarreal
 * @date 11/21/2020
 */
public class AnalogSignalWindow {

    private static final double radiusX = 20;
    private static final double radiusY = 340;

    public static void display(EthernetFrame ethernetFrame) {
        // Set up the window for the Analog Signal
        Stage signalWindow = new Stage();
        signalWindow.initModality(Modality.APPLICATION_MODAL);
        signalWindow.setTitle("Analog Signal in Binary ASK");
        // Create objects for the window
        VBox vbox = new VBox();
        String binStr = ethernetFrame.getMessage().getBinMessage();
        Label originalLabel = new Label("Original Message: " + ethernetFrame.getMessage().getMessage());
        Label binLabel = new Label("Binary Message: " + binStr);
        binLabel.setWrapText(true);
        Path path = AnalogSignalWindow.drawSineWave(binStr);
        Group pathGroup = new Group(path);
        Button button = new Button("Ok");
        button.setOnAction(e -> signalWindow.close());
        // Adjust the VBox
        vbox.getChildren().addAll(originalLabel, binLabel, pathGroup, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER_LEFT);
        // Put the vbox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        // Set up the scene for the window
        Scene scene = new Scene(scrollPane, 900, 300);
        signalWindow.setScene(scene);
        signalWindow.showAndWait();
    }

    /**
     * Draw the binary ASK sine wave as a Path object.
     * @param binStr
     * @return
     */
    private static Path drawSineWave(String binStr) {
        // Set up the path and initial moveTo
        int curX = 50;
        int fixedY = 50;
        Path path = new Path();
        MoveTo move = new MoveTo(curX, fixedY);
        path.getElements().add(move);
        // Iterate over the binary string and create the path elements for it
        int strLength = binStr.length();
        for (int i = 0; i < strLength; i++) {
            curX = curX + 25;
            char bit = binStr.charAt(i);
            ArrayList<PathElement> pathElements = drawNextPathElements(bit, curX, fixedY);
            for (PathElement pathElement : pathElements) {
                path.getElements().add(pathElement);
            }
        }
        return path;
    }

    /**
     * Draw the next PathElements based on the provided bit, and x and y axis.
     * @param bit
     * @param x
     * @param y
     * @return
     */
    private static ArrayList<PathElement> drawNextPathElements(char bit, int x, int y) {
        ArrayList<PathElement> pathElements = new ArrayList<>();
        // Draw a zero wave
        if (bit == '0') {
            HLineTo hLineTo = AnalogSignalWindow.drawZeroWave(x);
            pathElements.add(hLineTo);
        }
        // Draw a one wave
        else {
            ArrayList<ArcTo> arcTos = AnalogSignalWindow.drawOneWave(x, y);
            pathElements.addAll(arcTos);
        }
        return pathElements;
    }

    private static HLineTo drawZeroWave(int x) {
        return new HLineTo(x);
    }

    private static ArrayList<ArcTo> drawOneWave(int x, int y) {
        ArrayList<ArcTo> arcToList = new ArrayList<>();
        // Draw the first arc
        ArcTo arcto1 = new ArcTo();
        arcto1.setX(x - 12.5);
        arcto1.setY(y);
        arcto1.setRadiusX(radiusX);
        arcto1.setRadiusY(radiusY);
        // Draw the second arc
        ArcTo arcto2 = new ArcTo();
        arcto2.setX(x);
        arcto2.setY(y);
        arcto2.setRadiusX(radiusX);
        arcto2.setRadiusY(radiusY);
        arcto2.setSweepFlag(true); // Flip the arc on the x-axis
        // Return the list
        arcToList.add(arcto1);
        arcToList.add(arcto2);
        return arcToList;
    }
}

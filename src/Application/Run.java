package Application;

import GUI.*;
import Network.Utilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * This class runs the application. It contains the main method.
 * Date Modified: 10/13/2020
 * @author Fernando Villarreal
 */
public class Run extends Application {

    //========================================== CLASS VARIABLES ==========================================

    private final int mainWindowWidth = 1100;
    private final int mainWindowHeight = 490;
    private final int promptWindowWidth = 250;
    private final int promptWindowHeight = 150;

    //========================================== MAIN METHOD ==========================================

    public static void main(String[] args) {
        launch(args);
        //test01();
        //test02();
    }

    //========================================== START METHODS ==========================================

    @Override
    public void start(Stage stage) throws Exception {
        // Define the Main Pane
        MainGUI gui = new MainGUI();
        BorderPane mainPane = gui.getMainPane();
        // Set up the Scene with mainPane
        Scene mainScene = new Scene(mainPane, this.mainWindowWidth, this.mainWindowHeight);
        // Set up the Scenes with the promptPanes
        MessagePrompt promptB = new MessagePrompt(stage, mainScene, 'B');
        VBox promptPaneB = promptB.getPane();
        Scene promptSceneB = new Scene(promptPaneB, this.promptWindowWidth, this.promptWindowHeight);
        MessagePrompt promptA = new MessagePrompt(stage, promptSceneB, 'A');
        VBox promptPaneA = promptA.getPane();
        Scene promptSceneA = new Scene(promptPaneA, this.promptWindowWidth, this.promptWindowHeight);
        // Set up and show the stage
        stage.setTitle("Network Simulator");
        stage.setScene(promptSceneA);
        stage.show();
    }

    public static void test01() {
        int num1 = 255;
        int num2 = 255;
        String binnum1 = Utilities.convertIntToBin(num1);
        String binnum2 = Utilities.convertIntToBin(num2);
        String binResult = Utilities.addBinNums(binnum1, binnum2);
        int decResult = Utilities.convertBinToDec(binResult);
        System.out.println("decResult = " + decResult);
    }

    public static void test02() {
        NetworkMap networkMap = new NetworkMap();
        System.out.println(networkMap.toString() + "\n");
        Location host1 = NodeLocations.host1;
        Location host2 = NodeLocations.host2;
        ArrayList<Location> locationsPath = networkMap.dijkstra(host1, host2);
        System.out.println("Locations:\n" + locationsPath.toString());
    }
}

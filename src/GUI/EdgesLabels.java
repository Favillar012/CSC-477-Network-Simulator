package GUI;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class creates a set of twenty labels for the distances of each edge on the
 * network_image_3. The size of the window must be 1,100 by 490 for the edges to placed correctly.
 * @author Fernando Villarreal
 * @date 11/14/2020
 */
public class EdgesLabels {

    /**
     * Creates and returns a javafx.scene.Group object containing all the labels for the edges in
     * the network diagram (network_image_3.png).
     * @param distancesA
     * @param distancesB
     * @return
     */
    public static Group getEdgesLabels(ArrayList<Integer> distancesA, ArrayList<Integer> distancesB) {
        Group edgesGroup = new Group();
        // Edge 0 labels
        Label edge0A = new Label("" + distancesA.get(0));
        edge0A.setTextFill(Color.BLUE);
        edge0A.relocate(300, 350);
        Label edge0B = new Label("" + distancesB.get(0));
        edge0B.setTextFill(Color.RED);
        edge0B.relocate(300, 330);
        // Edge 1 labels
        Label edge1A = new Label("" + distancesA.get(1));
        edge1A.setTextFill(Color.BLUE);
        edge1A.relocate(415, 180);
        Label edge1B = new Label("" + distancesB.get(1));
        edge1B.setTextFill(Color.RED);
        edge1B.relocate(435, 180);
        // Edge 2 labels
        Label edge2A = new Label("" + distancesA.get(2));
        edge2A.setTextFill(Color.BLUE);
        edge2A.relocate(505, 240);
        Label edge2B = new Label("" + distancesB.get(2));
        edge2B.setTextFill(Color.RED);
        edge2B.relocate(520, 255);
        // Edge 3 labels
        Label edge3A = new Label("" + distancesA.get(3));
        edge3A.setTextFill(Color.BLUE);
        edge3A.relocate(535, 350);
        Label edge3B = new Label("" + distancesB.get(3));
        edge3B.setTextFill(Color.RED);
        edge3B.relocate(535, 330);
        // Edge 4 labels
        Label edge4A = new Label("" + distancesA.get(4));
        edge4A.setTextFill(Color.BLUE);
        edge4A.relocate(505, 180);
        Label edge4B = new Label("" + distancesB.get(4));
        edge4B.setTextFill(Color.RED);
        edge4B.relocate(520, 165);
        // Edge 5 labels
        Label edge5A = new Label("" + distancesA.get(5));
        edge5A.setTextFill(Color.BLUE);
        edge5A.relocate(535, 95);
        Label edge5B = new Label("" + distancesB.get(5));
        edge5B.setTextFill(Color.RED);
        edge5B.relocate(535, 75);
        // Edge 6 labels
        Label edge6A = new Label("" + distancesA.get(6));
        edge6A.setTextFill(Color.BLUE);
        edge6A.relocate(600, 180);
        Label edge6B = new Label("" + distancesB.get(6));
        edge6B.setTextFill(Color.RED);
        edge6B.relocate(585, 165);
        // Edge 7 labels
        Label edge7A = new Label("" + distancesA.get(7));
        edge7A.setTextFill(Color.BLUE);
        edge7A.relocate(685, 180);
        Label edge7B = new Label("" + distancesB.get(7));
        edge7B.setTextFill(Color.RED);
        edge7B.relocate(670, 180);
        // Edge 8 labels
        Label edge8A = new Label("" + distancesA.get(8));
        edge8A.setTextFill(Color.BLUE);
        edge8A.relocate(600, 240);
        Label edge8B = new Label("" + distancesB.get(8));
        edge8B.setTextFill(Color.RED);
        edge8B.relocate(585, 255);
        // Edge 9 labels
        Label edge9A = new Label("" + distancesA.get(9));
        edge9A.setTextFill(Color.BLUE);
        edge9A.relocate(785, 350);
        Label edge9B = new Label("" + distancesB.get(9));
        edge9B.setTextFill(Color.RED);
        edge9B.relocate(785, 330);
        // Add the labels to edgesGroup
        edgesGroup.getChildren().addAll(edge0A, edge0B, edge1A, edge1B, edge2A, edge2B, edge3A, edge3B);
        edgesGroup.getChildren().addAll(edge4A, edge4B, edge5A, edge5B, edge6A, edge6B, edge7A, edge7B);
        edgesGroup.getChildren().addAll(edge8A, edge8B, edge9A, edge9B);
        // Return edgesGroup
        return edgesGroup;
    }
}

package GUI;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

/**
 * This class creates a Circle to display in the GUI.
 * Date Modified: 9/12/2020
 * @author Fernando Villarreal
 */
public class DataCircle extends Circle {

    //============================= CLASS VARIABLES =============================

    //============================= CONSTRUCTORS =============================

    public DataCircle() {
        super();
    }

    public DataCircle(Color color, double radius) {
        super();
        this.setFill(color);
        this.setRadius(radius);
    }

    public DataCircle(Color color, double x, double y, double radius) {
        super();
        this.setFill(color);
        this.setRadius(radius);
        this.relocate(x, y);
    }

    //============================= METHODS =============================

}

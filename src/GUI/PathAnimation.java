package GUI;

import javafx.animation.PathTransition;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This class implements path animations for the DataCircle class in the GUI.
 * @author Fernando Villarreal
 * @date 11/10/2020
 */
public class PathAnimation {

    //================ STATIC VARIABLES ================

    public static final int STOPPED = 0;
    public static final int PAUSED = 1;
    public static final int RUNNING = 2;

    //================ CLASS VARIABLES ================

    private ArrayList<PathTransition> pathTransitions;
    private PathTransition currentPathTransition;
    private DataCircle dataCircle;
    private int status;

    //================ CONSTRUCTORS ================

    public PathAnimation(DataCircle dataCircle) {
        this.dataCircle = dataCircle;
        this.status = PathAnimation.STOPPED;
    }

    public PathAnimation(DataCircle dataCircle, ArrayList<Location> locations) {
        this.dataCircle = dataCircle;
        this.status = PathAnimation.STOPPED;
        this.setPath(locations);
    }

    //================ PUBLIC METHODS ================

    /**
     * Get the status of this PathAnimation.
     * @return
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Get the time that this animation lasts in seconds.
     * @return
     */
    public int getTimeOfAnimation() {
        // Each PathTransition lasts 3 seconds
        return this.pathTransitions.size() * 3;
    }

    /**
     * Plays the animation.
     */
    public void play() {
        if (this.status == PathAnimation.PAUSED || this.status == PathAnimation.STOPPED) {
            this.status = PathAnimation.RUNNING;
            this.currentPathTransition.play();
        }
    }

    /**
     * Pauses the animation.
     */
    public void pause() {
        // Pause the animation if it is running
        if (this.status == PathAnimation.RUNNING) {
            this.status = PathAnimation.PAUSED;
            this.currentPathTransition.pause();
        }
    }

    /**
     * Set the path for this PathAnimation using the provided list of Locations.
     * @param locations
     */
    public void setPath(ArrayList<Location> locations) {
        ArrayList<PathTransition> pathTransitionList = new ArrayList<>();
        int listSize = locations.size();
        for (int i = 0; i < listSize; i++) {
            Location location = locations.get(i);
            // Set the initial moveTo for the pathTransition
            Location prevLocation;
            MoveTo moveTo;
            if (i == 0) {
                prevLocation = new Location(0, 0);
                moveTo = new MoveTo(prevLocation.x(), prevLocation.y());
            } else {
                prevLocation = locations.get(i - 1);
                moveTo = new MoveTo(prevLocation.x(), prevLocation.y());
            }
            // Create the lineTo and path for the pathTransition
            LineTo lineTo = new LineTo(location.x(), location.y());
            Path path = new Path();
            path.getElements().addAll(moveTo, lineTo);
            PathTransition pathTransition = new PathTransition();
            pathTransition.setPath(path);
            int nextIndex = i + 1;
            // Set the pathTransition to play the next transition
            if (nextIndex < listSize) {
                this.setPathTransitionDefaultProperties(pathTransition);
                pathTransition.setOnFinished(e -> this.playNextTransition(nextIndex));
            }
            // Set the last pathTransition to do animationFinished()
            else {
                this.setPathTransitionDefaultProperties(pathTransition);
                pathTransition.setOnFinished(e -> this.animationFinished());
            }
            pathTransitionList.add(pathTransition);
        }
        this.pathTransitions = pathTransitionList;
        this.currentPathTransition = this.getPathTransition(0);
    }

    //================ PRIVATE METHODS ================

    private PathTransition getPathTransition(int index) {
        return this.pathTransitions.get(index);
    }

    /**
     * Set the default properties for a single PathTransition object.
     * @param pathTransition
     */
    private void setPathTransitionDefaultProperties(PathTransition pathTransition) {
        pathTransition.setNode(this.dataCircle);
        pathTransition.setDuration(Duration.seconds(3));
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setAutoReverse(false);
        pathTransition.setCycleCount(1);
    }

    /**
     * Play the next path transition.
     * @param index
     */
    private void playNextTransition(int index) {
        this.currentPathTransition = this.getPathTransition(index);
        this.currentPathTransition.play();
    }

    /**
     * Animation finished. Reset the animation.
     */
    private void animationFinished() {
        this.status = PathAnimation.STOPPED;
        this.currentPathTransition = this.getPathTransition(0);
    }
}

package GUI;

import Application.Simulator;
import GUI.LayerContentsWindows.*;
import Network.Datagrams.Datagram;
import Network.Message;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class for creating the GUI.
 * Date Modified: 11/21/2020
 * @author Fernando Villarreal
 */
public class MainGUI {

    //========================================== CLASS VARIABLES ==========================================

    // Main GUI Elements
    private final BorderPane mainPane;
    private final Button startBtn;
    private final Button pauseBtn;
    private DataCircle circleA;
    private DataCircle circleB;
    private Group formerEdgeLabels;
    // Animation
    private PathAnimation circlePathA;
    private PathAnimation circlePathB;
    private SequentialTransition timeTrans;
    // Network Maps A and B
    private NetworkMap networkMapA;
    private NetworkMap networkMapB;
    // Sender Buttons
    private final Button senderMessageBtn;
    private final Button senderTransportBtn;
    private final Button senderNetworkBtn;
    private final Button senderDataLinkBtn;
    private final Button senderPhysicalBtn;
    private final Button senderSignalBtn;
    // Receiver Buttons
    private final Button receiverMessageBtn;
    private final Button receiverTransportBtn;
    private final Button receiverNetworkBtn;
    private final Button receiverDataLinkBtn;
    private final Button receiverPhysicalBtn;
    private final Button receiverSignalBtn;
    // Messages and Datagrams
    private Message messageA;
    private Message messageB;
    private Datagram datagramA;
    private Datagram datagramB;

    //========================================== CONSTRUCTORS ==========================================

    public MainGUI() throws FileNotFoundException {
        // Initialize the mainPane and the buttons
        this.mainPane = new BorderPane();
        this.circleA = new DataCircle(Color.BLUE, 195, 45, 10);
        this.circleB = new DataCircle(Color.RED, 200, 45, 10);
        this.startBtn = new Button("Start Simulation");
        this.pauseBtn = new Button("Pause");
        this.senderMessageBtn = new Button("Message");
        this.senderTransportBtn = new Button("Transport");
        this.senderNetworkBtn = new Button("Network");
        this.senderDataLinkBtn = new Button("Data-Link");
        this.senderPhysicalBtn = new Button("Physical");
        this.senderSignalBtn = new Button("Signal");
        this.receiverMessageBtn = new Button("Message");
        this.receiverTransportBtn = new Button("Transport");
        this.receiverNetworkBtn = new Button("Network");
        this.receiverDataLinkBtn = new Button("Data-Link");
        this.receiverPhysicalBtn = new Button("Physical");
        this.receiverSignalBtn = new Button("Signal");
        // Put the 'Start' and 'Pause' buttons in an HBox for the bottom pane
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(this.startBtn, this.pauseBtn);
        this.mainPane.setBottom(hbox);
        // Set the actions of the 'Start' and 'Pause' buttons
        this.startBtn.setOnAction(e -> this.startSimulation());
        this.pauseBtn.setOnAction(e -> this.pauseOrResumeAnimation());
        // Put the Sender's buttons in a VBox for the left pane
        Label senderLabel = new Label("Sender");
        VBox vbox1 = new VBox();
        vbox1.setPadding(new Insets(10));
        vbox1.setSpacing(8);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.getChildren().addAll(senderLabel, this.senderMessageBtn, this.senderTransportBtn, this.senderNetworkBtn);
        vbox1.getChildren().addAll(this.senderDataLinkBtn, this.senderPhysicalBtn, this.senderSignalBtn);
        this.mainPane.setLeft(vbox1);
        // Set the actions of the Sender's buttons
        this.senderMessageBtn.setOnAction(e -> this.showMessageContents(DatagramCopies.senderNum));
        this.senderTransportBtn.setOnAction(e -> this.showTransportLayer(DatagramCopies.senderNum));
        this.senderNetworkBtn.setOnAction(e -> this.showNetworkLayer(DatagramCopies.senderNum));
        this.senderDataLinkBtn.setOnAction(e -> this.showDataLinkLayer(DatagramCopies.senderNum));
        this.senderPhysicalBtn.setOnAction(e -> this.showPhysicalLayer(DatagramCopies.senderNum));
        this.senderSignalBtn.setOnAction(e -> this.showSignalWindow(DatagramCopies.senderNum));
        // Put the both the Receivers' buttons in a VBox for the right pane
        Label recLabel = new Label("Receiver");
        VBox vbox2 = new VBox();
        vbox2.setPadding(new Insets(10));
        vbox2.setSpacing(8);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(recLabel, this.receiverMessageBtn, this.receiverTransportBtn, this.receiverNetworkBtn);
        vbox2.getChildren().addAll(this.receiverDataLinkBtn, this.receiverPhysicalBtn, this.receiverSignalBtn);
        this.mainPane.setRight(vbox2);
        // Set the actions of the Receiver's buttons
        this.receiverMessageBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverTransportBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverNetworkBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverDataLinkBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverPhysicalBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverSignalBtn.setOnAction(e -> this.showNoMessagePresent());
        // Get the image and set it as a background
        Image image = this.getNetworkImage();
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                             BackgroundRepeat.NO_REPEAT,
                                             BackgroundRepeat.NO_REPEAT,
                                             BackgroundPosition.CENTER,
                                                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        // Add a background the center pane
        Pane centerPane = new Pane();
        centerPane.setBackground(background);
        this.mainPane.setCenter(centerPane);
        // Initialize the circlePaths
        this.circlePathA = new PathAnimation(this.circleA);
        this.circlePathB = new PathAnimation(this.circleB);
        // Add the DataCircles to the GUI
        this.mainPane.getChildren().addAll(this.circleA, this.circleB);
    }

    //========================================== METHODS ==========================================

    //==================== START SIMULATION ====================

    /**
     * Starts the simulation.
     */
    private void startSimulation() {
        if (this.getAnimationStatus() == PathAnimation.STOPPED) {
            this.dijkstra();
            this.resetReceiverButtons();
            this.playAnimation();
            this.messageA = new Message(MessagePrompt.getMessageA());
            this.messageB = new Message(MessagePrompt.getMessageB());
            Simulator simulator = new Simulator(this, this.timeTrans);
            simulator.startSimulation();
            this.datagramA = simulator.getDatagramA();
            this.datagramB = simulator.getDatagramB();
        } // Else, do nothing
    }

    //==================== SHOW MESSAGE CONTENTS AT LAYERS ====================

    /**
     * Show the No Message Present window.
     */
    public void showNoMessagePresent() {
        // If the Animation is running, pause and then play the animation.
        if (this.getAnimationStatus() == PathAnimation.RUNNING) {
            this.pauseAnimation();
            NoMessagePresent.display();
            this.playAnimation();
        // If the Animation is paused or stopped, just show the message contents.
        } else if (this.getAnimationStatus() == PathAnimation.PAUSED || this.getAnimationStatus() == PathAnimation.STOPPED) {
            NoMessagePresent.display();
        } // Else, ignore the button press.
    }

    /**
     * Show the message contents in a separate window.
     */
    public void showMessageContents(int hostNum) {
        this.showMessageContentsHelper(hostNum, DatagramCopies.datagramANum);
        this.showMessageContentsHelper(hostNum, DatagramCopies.datagramBNum);
    }

    private void showMessageContentsHelper(int hostNum, int datagramNum) {
        try {
            // If the Animation is running, pause and then play the animation.
            if (this.getAnimationStatus() == PathAnimation.RUNNING) {
                this.pauseAnimation();
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    MessageContents.display(DatagramCopies.getSenderBaseDatagram(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    MessageContents.display(DatagramCopies.getReceiverBaseDatagram(datagramNum));
                } else {
                    System.err.println("ERROR! : hostNum=" + hostNum);
                    NoMessagePresent.display();
                }
                this.playAnimation();
            // If the Animation is paused or stopped, just show the message contents.
            } else if (this.getAnimationStatus() == PathAnimation.PAUSED || this.getAnimationStatus() == PathAnimation.STOPPED) {
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    MessageContents.display(DatagramCopies.getSenderBaseDatagram(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    MessageContents.display(DatagramCopies.getReceiverBaseDatagram(datagramNum));
                } else {
                    System.err.println("ERROR! : hostNum=" + hostNum);
                    NoMessagePresent.display();
                }
            } // Else, ignore the button press.
        } catch (NullPointerException ex) {
            NoMessagePresent.display();
        }
    }

    /**
     * Show the message contents in the Transport Layer.
     */
    public void showTransportLayer(int hostNum) {
        this.showTransportLayerHelper(hostNum, DatagramCopies.datagramANum);
        this.showTransportLayerHelper(hostNum, DatagramCopies.datagramBNum);
    }

    private void showTransportLayerHelper(int hostNum, int datagramNum) {
        try {
            // If the Animation is running, pause and then play the animation.
            if (this.getAnimationStatus() == PathAnimation.RUNNING) {
                this.pauseAnimation();
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    TransportContents.display(DatagramCopies.getSenderTransDatagram(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    TransportContents.display(DatagramCopies.getReceiverTransDatagram(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
                this.playAnimation();
            // If the Animation is paused or stopped, just show the message contents at the transport layer.
            } else if (this.getAnimationStatus() == PathAnimation.PAUSED || this.getAnimationStatus() == PathAnimation.STOPPED) {
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    TransportContents.display(DatagramCopies.getSenderTransDatagram(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    TransportContents.display(DatagramCopies.getReceiverTransDatagram(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
            } // Else, ignore the button press.
        } catch (NullPointerException ex) {
            NoMessagePresent.display();
        }
    }

    /**
     * Show the message contents at the Network Layer.
     */
    public void showNetworkLayer(int hostNum) {
        this.showNetworkLayerHelper(hostNum, DatagramCopies.datagramANum);
        this.showNetworkLayerHelper(hostNum, DatagramCopies.datagramBNum);
    }

    private void showNetworkLayerHelper(int hostNum, int datagramNum) {
        try {
            // If the Animation is running, pause and then play the animation.
            if (this.getAnimationStatus() == PathAnimation.RUNNING) {
                this.pauseAnimation();
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    NetworkContents.display(DatagramCopies.getSenderNetDatagram(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    NetworkContents.display(DatagramCopies.getReceiverNetDatagram(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
                this.playAnimation();
            // If the Animation is paused or stopped, just show the message contents at the transport layer.
            } else if (this.getAnimationStatus() == PathAnimation.PAUSED || this.getAnimationStatus() == PathAnimation.STOPPED) {
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    NetworkContents.display(DatagramCopies.getSenderNetDatagram(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    NetworkContents.display(DatagramCopies.getReceiverNetDatagram(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
            } // Else, ignore the button press.
        } catch (NullPointerException ex) {
            NoMessagePresent.display();
        }
    }

    /**
     * Show the message contents at the Data-Link Layer.
     * @param hostNum
     */
    public void showDataLinkLayer(int hostNum) {
        this.showDataLinkLayerHelper(hostNum, DatagramCopies.datagramANum);
        this.showDataLinkLayerHelper(hostNum, DatagramCopies.datagramBNum);
    }

    private void showDataLinkLayerHelper(int hostNum, int datagramNum) {
        try {
            // If the Animation is running, pause and then play the animation.
            if (this.getAnimationStatus() == PathAnimation.RUNNING) {
                this.pauseAnimation();
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    DataLinkContents.display(DatagramCopies.getSenderEthernetFrame(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    DataLinkContents.display(DatagramCopies.getReceiverEthernetFrame(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
                this.playAnimation();
            // If the Animation is paused or stopped, just show the window.
            } else if (this.getAnimationStatus() == PathAnimation.PAUSED || this.getAnimationStatus() == PathAnimation.STOPPED) {
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    DataLinkContents.display(DatagramCopies.getSenderEthernetFrame(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    DataLinkContents.display(DatagramCopies.getReceiverEthernetFrame(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
            } // Else, ignore the button press.
        } catch (NullPointerException ex) {
            NoMessagePresent.display();
        }
    }

    /**
     * Show the bit sequence at the Physical Layer.
     * @param hostNum
     */
    public void showPhysicalLayer(int hostNum) {
        this.showPhysicalLayerHelper(hostNum, DatagramCopies.datagramANum);
        this.showPhysicalLayerHelper(hostNum, DatagramCopies.datagramBNum);
    }

    private void showPhysicalLayerHelper(int hostNum, int datagramNum) {
        try {
            // If the Animation is running, pause and then play the animation.
            if (this.getAnimationStatus() == PathAnimation.RUNNING) {
                this.pauseAnimation();
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    PhysicalContents.display(DatagramCopies.getSenderEthernetFrame(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    PhysicalContents.display(DatagramCopies.getReceiverEthernetFrame(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
                this.playAnimation();
            // If the Animation is paused or stopped, just show the window.
            } else if (this.getAnimationStatus() == PathAnimation.PAUSED || this.getAnimationStatus() == PathAnimation.STOPPED) {
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    PhysicalContents.display(DatagramCopies.getSenderEthernetFrame(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    PhysicalContents.display(DatagramCopies.getReceiverEthernetFrame(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
            } // Else, ignore the button press.
        } catch (NullPointerException ex) {
            NoMessagePresent.display();
        }
    }

    /**
     * Show the analog signal generated in Binary ASK.
     * @param hostNum
     */
    public void showSignalWindow(int hostNum) {
        this.showSignalWindowHelper(hostNum, DatagramCopies.datagramANum);
        this.showSignalWindowHelper(hostNum, DatagramCopies.datagramBNum);
    }

    private void showSignalWindowHelper(int hostNum, int datagramNum) {
        try {
            // If the Animation is running, pause and then play the animation.
            if (this.getAnimationStatus() == PathAnimation.RUNNING) {
                this.pauseAnimation();
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    AnalogSignalWindow.display(DatagramCopies.getSenderEthernetFrame(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    AnalogSignalWindow.display(DatagramCopies.getReceiverEthernetFrame(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
                this.playAnimation();
            // If the Animation is paused or stopped, just show the window.
            } else if (this.getAnimationStatus() == PathAnimation.PAUSED || this.getAnimationStatus() == PathAnimation.STOPPED) {
                // Show the window
                if (hostNum == DatagramCopies.senderNum) {
                    AnalogSignalWindow.display(DatagramCopies.getSenderEthernetFrame(datagramNum));
                } else if (hostNum == DatagramCopies.receiverNum) {
                    AnalogSignalWindow.display(DatagramCopies.getReceiverEthernetFrame(datagramNum));
                } else {
                    NoMessagePresent.display();
                }
            } else {
                NoMessagePresent.display();
            }
        } catch (NullPointerException ex) {
            NoMessagePresent.display();
        }
    }

    //==================== PLAY OR PAUSE ANIMATION ====================

    /**
     * Pauses or Resumes the animation.
     */
    private void pauseOrResumeAnimation() {
        if (this.getAnimationStatus() == PathAnimation.RUNNING) {
            this.pauseAnimation();
        } else if (this.getAnimationStatus() == PathAnimation.PAUSED) {
            this.playAnimation();
        } // Else, do nothing
    }

    /**
     * Plays the animation.
     */
    public void playAnimation() {
        System.out.println("Playing Animation...");
        this.circlePathA.play();
        this.circlePathB.play();
        this.timeTrans.play();
    }

    /**
     * Pauses the animation.
     */
    public void pauseAnimation() {
        System.out.println("Pause Animation.");
        this.circlePathA.pause();
        this.circlePathB.pause();
        this.timeTrans.pause();
    }

    /**
     * Get the combined status of both circlePath animations (A and B).
     * @return
     */
    private int getAnimationStatus() {
        int statusA = this.circlePathA.getStatus();
        int statusB = this.circlePathB.getStatus();
        // Compare the status of both animations
        if (statusA == PathAnimation.RUNNING || statusB == PathAnimation.RUNNING) {
            return PathAnimation.RUNNING;
        } else if (statusA == PathAnimation.PAUSED || statusB == PathAnimation.PAUSED) {
            return PathAnimation.PAUSED;
        } else {
            return PathAnimation.STOPPED;
        }
    }

    //==================== SIMULATION RESET ====================

    /**
     * Resets the actions of the receiver's buttons.
     */
    public void resetReceiverButtons() {
        this.receiverMessageBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverTransportBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverNetworkBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverDataLinkBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverPhysicalBtn.setOnAction(e -> this.showNoMessagePresent());
        this.receiverSignalBtn.setOnAction(e -> this.showNoMessagePresent());
    }

    //=================== OTHER PRIVATE METHODS ===================

    /**
     * Perform Dijkstra's algorithm. Set the labels for the edges in the network diagram. Create the
     * PathAnimation objects for the DataCircles. Add the DataCircle objects to the GUI.
     */
    private void dijkstra() {
        Location startLocation = NodeLocations.host1;
        Location endLocation = NodeLocations.host2;
        // Initialize the NetworkMap objects and perform dijkstra's algorithm
        this.networkMapA = new NetworkMap();
        this.networkMapB = new NetworkMap();
        ArrayList<Location> pathA = this.networkMapA.dijkstra(startLocation, endLocation);
        ArrayList<Location> pathB = this.networkMapB.dijkstra(startLocation, endLocation);
        // Get the edge distances for the labels and set them
        ArrayList<Integer> edgeDistancesA = this.networkMapA.getEdgeDistances();
        ArrayList<Integer> edgeDistancesB = this.networkMapB.getEdgeDistances();
        this.setEdgeLabels(edgeDistancesA, edgeDistancesB);
        // Create the PathAnimation objects for DataCircles A and B
        this.circlePathA = this.createPathAnimationA(pathA);
        this.circlePathB = this.createPathAnimationB(pathB);
        // Set the length for the animation
        int animationLength = this.calculateAnimationLength();
        this.timeTrans = this.createTimeTrans(animationLength);
    }

    /**
     * Sets the labels for the edges in the network image.
     */
    private void setEdgeLabels(ArrayList<Integer> distancesA, ArrayList<Integer> distancesB) {
        if (this.formerEdgeLabels != null) {
            this.mainPane.getChildren().remove(this.formerEdgeLabels);
        }
        Group edgesLabels = EdgesLabels.getEdgesLabels(distancesA, distancesB);
        this.formerEdgeLabels = edgesLabels;
        this.mainPane.getChildren().add(edgesLabels);
    }

    /**
     * Creates and returns a PathAnimation object for the animation of the DataCircle A.
     * @return PathTransition
     */
    private PathAnimation createPathAnimationA(ArrayList<Location> pathLocations) {
        PathAnimation pathAnimation = new PathAnimation(this.circleA);
        // Set the path for the pathAnimation and return it
        pathAnimation.setPath(pathLocations);
        return pathAnimation;
    }

    /**
     * Creates and returns a PathTransition for the animation of the DataCircle B.
     * @return PathTransition
     */
    private PathAnimation createPathAnimationB(ArrayList<Location> pathLocations) {
        PathAnimation pathAnimation = new PathAnimation(this.circleB);
        // Set the path for the pathAnimation and return it
        pathAnimation.setPath(pathLocations);
        return pathAnimation;
    }

    private int calculateAnimationLength() {
        int lengthA = this.circlePathA.getTimeOfAnimation();
        int lengthB = this.circlePathB.getTimeOfAnimation();
        if (lengthA >= lengthB) {
            return lengthA;
        }
        return lengthB;
    }

    private SequentialTransition createTimeTrans(int time) {
        PauseTransition pt1 = new PauseTransition(Duration.seconds(time));
        SequentialTransition timeTrans = new SequentialTransition(pt1);
        return timeTrans;
    }

    private Image getNetworkImage() {
        InputStream inputStream1 = this.getClass().getResourceAsStream("/GUI/network_image_3.png");
        //InputStream inputStream2 = MainGUI.class.getResourceAsStream("/network_image.png");
        //InputStream inputStream3 = Thread.currentThread().getContextClassLoader().getResourceAsStream("/network_image.png");
        //FileInputStream input = new FileInputStream("network_image.png");
        Image image = new Image(inputStream1);
        return image;
    }

    //=================== GETTERS ===================

    public DataCircle getDataCircle() {
        return this.circleA;
    }

    public PathAnimation getCircleAnimation() {
        return this.circlePathA;
    }

    public Message getMessageA() {
        return this.messageA;
    }

    public Message getMessageB() {
        return this.messageB;
    }

    /**
     * Returns the Main Pane for the GUI.
     * @return
     */
    public BorderPane getMainPane() {
        return this.mainPane;
    }

    //============== BUTTON GETTERS ==============

    public Button getReceiverMessageBtn() {
        return this.receiverMessageBtn;
    }

    public Button getReceiverTransportBtn() {
        return this.receiverTransportBtn;
    }

    public Button getReceiverNetworkBtn() {
        return this.receiverNetworkBtn;
    }

    public Button getReceiverDataLinkBtn() {
        return this.receiverDataLinkBtn;
    }

    public Button getReceiverPhysicalBtn() {
        return this.receiverPhysicalBtn;
    }

    public Button getReceiverSignalBtn() {
        return this.receiverSignalBtn;
    }
}

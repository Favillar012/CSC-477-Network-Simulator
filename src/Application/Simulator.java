package Application;

import GUI.DatagramCopies;
import GUI.MainGUI;
import Network.*;
import Network.Datagrams.Datagram;
import Network.Datagrams.EthernetFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

/**
 * This class carries out the network simulation.
 * Date Modified: 10/20/2020
 * @author Fernando Villarreal
 */
public class Simulator {

    //============================= CLASS VARIABLES =============================

    // Main GUI Elements
    private final MainGUI gui;
    private Button recMessageBtn;
    private Button recTransportBtn;
    private Button recNetworkBtn;
    private Button recDataLinkBtn;
    private Button recPhysicalBtn;
    private Button recSignalBtn;
    // Animation
    private final SequentialTransition timeTrans;
    private PauseTransition checkMailTimer;
    // Messages, Datagrams, and Mailbox
    private final Message messageA;
    private final Message messageB;
    private Datagram datagramA;
    private Datagram datagramB;
    private Mailbox mailbox;
    // Sender, Receiver, and Routers
    private Host sender;
    private Host receiver;
    private final String sourceIP = "200.23.56.8";
    private final String sourcePort = "69";
    private final String sourceLink = "4A:30:10:21:10:1A";
    private final String destinationIp = "193.14.26.7";
    private final String destinationPort = "13";
    private final String destinationLink = "47:20:1B:2E:08:EE";

    //============================= CONSTRUCTOR =============================

    public Simulator(MainGUI gui, SequentialTransition timeTrans) {
        this.gui = gui;
        this.messageA = gui.getMessageA();
        this.messageB = gui.getMessageB();
        this.datagramA = new Datagram(messageA);
        this.datagramB = new Datagram(messageB);
        // Get the receiver's buttons
        this.recMessageBtn = gui.getReceiverMessageBtn();
        this.recTransportBtn = gui.getReceiverTransportBtn();
        this.recNetworkBtn = gui.getReceiverNetworkBtn();
        this.recDataLinkBtn = gui.getReceiverDataLinkBtn();
        this.recPhysicalBtn = gui.getReceiverPhysicalBtn();
        this.recSignalBtn = gui.getReceiverSignalBtn();
        // Create mailbox and timers
        this.timeTrans = timeTrans;
        this.checkMailTimer = new PauseTransition(Duration.seconds(5));
        this.mailbox = new Mailbox();
        // Set the action for timeTrans and checkMailTimer
        this.timeTrans.setOnFinished(e -> this.messageSent());
        this.checkMailTimer.setOnFinished(e -> this.everyoneCheckMail());
    }

    //============================= METHODS =============================

    /**
     * Starts the simulation.
     */
    public void startSimulation() {
        // Initialize the Datagrams and Mailbox
        this.datagramA.setSourceSocket(this.sourceIP, this.sourcePort, this.sourceLink);
        this.datagramA.setDestinationSocket(this.destinationIp, this.destinationPort, this.destinationLink);
        this.datagramB.setSourceSocket(this.sourceIP, this.sourcePort, this.sourceLink);
        this.datagramB.setDestinationSocket(this.destinationIp, this.destinationPort, this.destinationLink);
        this.mailbox = new Mailbox();
        // Initialize the Sender and Router
        this.sender = new Host(this.sourceIP, this.sourceLink, false);
        EthernetFrame frameA = this.sender.sendMessage(this.datagramA, this.mailbox);
        EthernetFrame frameB = this.sender.sendMessage(this.datagramB, this.mailbox);
        this.datagramA = frameA;
        this.datagramB = frameB;
        // Set the Sender's Datagram Copies
        DatagramCopies.setSenderDatagrams(frameA, frameB);
        // Initialize the receivers
        System.out.println("Message A to be sent: " + frameA.getMessage().toString());
        System.out.println("Message B to be sent: " + frameB.getMessage().toString());
        this.receiver = new Host(this.destinationIp, this.destinationLink, false);
        // Deliver the datagrams to the receiver
        this.timeTrans.play();
        //this.checkMailTimer.play();
    }

    public Datagram getDatagramA() {
        return this.datagramA;
    }

    public Datagram getDatagramB() {
        return datagramB;
    }

    /**
     * Give the message to the Receiver.
     */
    private void messageSent() {
        // Give the datagrams to the receiver
        boolean acceptedA = this.receiver.receiveMessage(this.mailbox);
        EthernetFrame frameA = this.receiver.getRecentDatagram();
        boolean acceptedB = this.receiver.receiveMessage(this.mailbox);
        EthernetFrame frameB = this.receiver.getRecentDatagram();
        // Set the receivers' Datagram Copies
        DatagramCopies.setReceiverDatagrams(frameA, frameB);
        // Set the new actions for receiver's buttons
        this.recMessageBtn.setOnAction(e -> this.gui.showMessageContents(DatagramCopies.receiverNum));
        this.recTransportBtn.setOnAction(e -> this.gui.showTransportLayer(DatagramCopies.receiverNum));
        this.recNetworkBtn.setOnAction(e -> this.gui.showNetworkLayer(DatagramCopies.receiverNum));
        this.recDataLinkBtn.setOnAction(e -> this.gui.showDataLinkLayer(DatagramCopies.receiverNum));
        this.recPhysicalBtn.setOnAction(e -> this.gui.showPhysicalLayer(DatagramCopies.receiverNum));
        this.recSignalBtn.setOnAction(e -> this.gui.showSignalWindow(DatagramCopies.receiverNum));
        // Print Results
        if (acceptedA) {
            System.out.println("The Receiver has accepted message A.");
        } else {
            System.out.println("The Receiver has rejected message B.");
        }
        if (acceptedB) {
            System.out.println("The Receiver has accepted message B.");
        } else {
            System.out.println("The Receiver has rejected message B.");
        }
    }

    /**
     * This method causes all routers and hosts in the network to check for mail.
     */
    private void everyoneCheckMail() {
        if (this.sender.checkMail(this.mailbox)) {
            this.sender.receiveMessage(this.mailbox);
        } else if (this.receiver.checkMail(this.mailbox)) {
            this.receiver.receiveMessage(this.mailbox);
        }
    }
}

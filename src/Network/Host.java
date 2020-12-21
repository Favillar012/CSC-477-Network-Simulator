package Network;

import Network.Datagrams.Datagram;
import Network.Datagrams.EthernetFrame;
import Network.Datagrams.IPDatagram;
import Network.Datagrams.UserDatagram;
import Network.Layers.DataLinkLayer;
import Network.Layers.NetworkLayer;
import Network.Layers.PhysicalLayer;
import Network.Layers.TransportLayer;

import java.util.ArrayList;

/**
 * This class implements Host objects in a network. Hosts can be senders or receivers.
 * @author Fernando Villarreal
 * @date 10/24/2020
 */
public class Host {

    //============================= CLASS VARIABLES =============================

    // Host Addresses
    private String ipAddress;
    private String linkAddress;
    // TCP/IP Layers
    private TransportLayer transportLayer;
    private NetworkLayer networkLayer;
    private DataLinkLayer dataLinkLayer;
    private PhysicalLayer physicalLayer;
    // Messages and Datagrams
    private ArrayList<Message> messagesReceived;
    private Message lastMessage;
    private EthernetFrame recentDatagram;
    // Is the host a router
    private boolean isRouter;


    //============================= CONSTRUCTOR =============================

    public Host(String ipAddress, String linkAddress, boolean isRouter) {
        this.ipAddress = ipAddress;
        this.linkAddress = linkAddress;
        this.transportLayer = new TransportLayer();
        this.networkLayer = new NetworkLayer(ipAddress, isRouter);
        this.dataLinkLayer = new DataLinkLayer(linkAddress);
        this.physicalLayer = new PhysicalLayer();
        this.messagesReceived = new ArrayList<>();
        this.isRouter = isRouter;
    }

    //============================= METHODS =============================

    /**
     * Send the provided datagram to the specified mailbox. The provided datagram must have the
     * following information: Source and Destination Sockets.
     * @param baseDatagram
     * @param mailbox
     * @return
     */
    public EthernetFrame sendMessage(Datagram baseDatagram, Mailbox mailbox) {
        Datagram datagram = new Datagram(baseDatagram);
        EthernetFrame ethernetFrame = this.deliverMail(datagram, mailbox);
        return ethernetFrame;
    }

    /**
     * Check for and receive mail from the mailbox. If mail was received, use this.getMessageToRead() or
     * this.getReceivedMessage to access the received message.
     * @param mailbox
     * @return
     */
    public boolean receiveMessage(Mailbox mailbox) {
        boolean mailForMe = this.checkMail(mailbox);
        if (mailForMe) {
            this.receiveMail(mailbox);
            return true;
        }
        return false;
    }

    /**
     * Check for mail in the mailbox.
     * @param mailbox
     * @return
     */
    public boolean checkMail(Mailbox mailbox) {
        boolean mailForMe = mailbox.checkMail(this.ipAddress);
        return mailForMe;
    }

    /**
     * Get the last message received.
     * @return
     */
    public Message getLastMessage() {
        return this.lastMessage;
    }

    /**
     * Get a received message using the provided index.
     * @param index
     * @return
     */
    public Message getReceivedMessage(int index) {
        return this.messagesReceived.get(index);
    }

    public EthernetFrame getRecentDatagram() {
        return this.recentDatagram;
    }

    /**
     * Check if this host object is a router. Returns true if it is. Returns false
     * if it is not.
     * @return
     */
    public boolean isRouter() {
        return this.isRouter;
    }

    //=================== PRIVATE METHODS ===================

    /**
     * Prepare the message for delivery (Create the EthernetFrame).
     * @param datagram
     * @param mailbox
     * @return
     */
    private EthernetFrame deliverMail(Datagram datagram, Mailbox mailbox) {
        // Transport Layer
        this.transportLayer.setData(datagram);
        this.transportLayer.prepareMessage();
        UserDatagram userDatagram = this.transportLayer.getUserDatagram();
        // Network Layer
        this.networkLayer.setData(userDatagram);
        this.networkLayer.prepareMessage();
        IPDatagram ipDatagram = this.networkLayer.getIPDatagram();
        // Data-Link Layer
        this.dataLinkLayer.prepareMessage(ipDatagram);
        EthernetFrame ethernetFrame = this.dataLinkLayer.getEthernetFrame();
        // Physical Layer
        this.physicalLayer.prepareMessage(ethernetFrame);
        ethernetFrame = this.physicalLayer.getEthernetFrame();
        // Put the EthernetFrame in the mailbox for delivery
        this.recentDatagram = ethernetFrame;
        mailbox.putDatagram(ethernetFrame);
        // Return the EthernetFrame
        return ethernetFrame;
    }

    private Message receiveMail(Mailbox mailbox) {
        // Get the datagram from the mailbox
        EthernetFrame ethernetFrame = mailbox.getDatagram(this.ipAddress);
        this.recentDatagram = ethernetFrame;
        /** Process the message after receiving it (Get the Message) **/
        // Data-Link Layer
        this.dataLinkLayer.receiveMessage(ethernetFrame);
        IPDatagram ipDatagram = this.dataLinkLayer.getData();
        // Network Layer
        this.networkLayer.setDatagram(ipDatagram);
        this.networkLayer.receiveMessage();
        UserDatagram userDatagram = this.networkLayer.getData();
        // Transport Layer
        this.transportLayer.setUserDatagram(userDatagram);
        this.transportLayer.receiveMessage();
        Datagram datagram = this.transportLayer.getData();
        Message message = datagram.getMessage();
        this.messagesReceived.add(message);
        this.lastMessage = message;
        return message;
    }
}

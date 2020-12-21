package Network;

import Network.Datagrams.EthernetFrame;

import java.util.ArrayList;

/**
 * This mailbox object will contain datagrams that are sent over the network. Hosts and routers should
 * check the mailbox for messages periodically.
 * @author Fernando Villarreal
 * @date 10/15/2020
 */
public class Mailbox {

    //============== CLASS VARIABLES ==============

    private ArrayList<EthernetFrame> mailbox;

    //============== CONSTRUCTORS ==============

    public Mailbox() {
        this.mailbox = new ArrayList<>();
    }

    //============== METHODS ==============

    /**
     * Put the given datagram in the mailbox.
     * @param datagram
     */
    public void putDatagram(EthernetFrame datagram) {
        this.mailbox.add(datagram);
    }

    /**
     * Check the mailbox to see if there is a message for the corresponding receiver.
     * @param receiverAddress
     * @return
     */
    public boolean checkMail(String receiverAddress) {
        // First, look for corresponding nextHopAddress.
        for (EthernetFrame datagram : this.mailbox) {
            String nextHopAddress = datagram.getNextHopAddress();
            if (nextHopAddress.compareTo(receiverAddress) == 0) {
                return true;
            }
        }
        // Otherwise, look for the corresponding destination IP address.
        for (EthernetFrame datagram : this.mailbox) {
            String destinationIPAddress = datagram.getDestinationIP();
            if (destinationIPAddress.compareTo(receiverAddress) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the datagram for the corresponding receiver. Returns null if there is no mail for
     * the specified receiver.
     * @param receiverAddress
     * @return
     */
    public EthernetFrame getDatagram(String receiverAddress) {
        // First, look for corresponding nextHopAddress.
        for (EthernetFrame datagram : this.mailbox) {
            String nextHopAddress = datagram.getNextHopAddress();
            if (nextHopAddress.compareTo(receiverAddress) == 0) {
                this.mailbox.remove(datagram);
                return datagram;
            }
        }
        // Otherwise, look for the corresponding destination IP address.
        for (EthernetFrame datagram : this.mailbox) {
            if (datagram.getDestinationIP().compareTo(receiverAddress) == 0) {
                this.mailbox.remove(datagram);
                return datagram;
            }
        }
        return null;
    }
}

package Network.Datagrams;

import Network.Message;

import java.util.ArrayList;

/**
 * This is the base Datagram object.
 * Date Modified: 10/24/2020
 * @author Fernando Villarreal
 */
public class Datagram {

    //============================= CLASS VARIABLES =============================

    private Message message;
    private String sourceIP;
    private String sourcePort;
    private String sourceLink;
    private String nextHopAddress;
    private DestinationInfo destinationInfo;

    //============================= CONSTRUCTOR =============================

    public Datagram() {}

    public Datagram(Message message) {
        this.message = message;
        this.nextHopAddress = "";
        this.destinationInfo = new DestinationInfo();
    }

    public Datagram(Datagram datagram) {
        this.message = datagram.getMessage();
        this.sourceIP = datagram.getSourceIP();
        this.sourcePort = datagram.getSourcePort();
        this.nextHopAddress = datagram.getNextHopAddress();
        this.destinationInfo = datagram.getDestinationInfo();
    }

    //============================= MAIN METHODS =============================

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public String getSourcePort() {
        return this.sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getSourceLink() {
        return this.sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getDestinationIP() {
        return this.destinationInfo.getIpAddress();
    }

    public void setDestinationIP(String destinationIP) {
        this.destinationInfo.setIpAddress(destinationIP);
    }

    public String getDestinationPort() {
        return this.destinationInfo.getPortNumber();
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationInfo.setPortNumber(destinationPort);
    }

    public String getDestinationLink() {
        return this.destinationInfo.getLinkAddress();
    }

    public void setDestinationLink(String destinationLink) {
        this.destinationInfo.setLinkAddress(destinationLink);
    }

    public void setSourceSocket(String ip, String port, String link) {
        this.setSourceIP(ip);
        this.setSourcePort(port);
        this.setSourceLink(link);
    }

    public void setDestinationSocket(String ip, String port, String link) {
        this.setDestinationIP(ip);
        this.setDestinationPort(port);
        this.setDestinationLink(link);
    }

    /**
     * Get the IP address of the next host/router to hop to.
     * @return
     */
    public String getNextHopAddress() {
        return this.nextHopAddress;
    }

    /**
     * Set the IP address of the next host/router to hop to.
     * @param nextHopAddress
     */
    public void setNextHopAddress(String nextHopAddress) {
        this.nextHopAddress = nextHopAddress;
    }

    /**
     * Checks if this datagram has an IP address for the next host/router to hop to.
     * @return
     */
    public boolean hasNextHopAddress() {
        if (this.nextHopAddress != null) {
            if (this.nextHopAddress.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes this datagram's nextHopAddress(The IP address of the next host/router to hop to).
     */
    public void removeNextHopAddress() {
        this.nextHopAddress = "";
    }

    //==================== ROUTE AND DESTINATION RELATED METHODS ====================


    public DestinationInfo getDestinationInfo() {
        return this.destinationInfo;
    }

    /**
     * Get the list router IP addresses.
     * @return
     */
    public ArrayList<String> getRouterIPs() {
        return this.destinationInfo.getRouterIPs();
    }

    /**
     * Set the list of router IP addresses.
     * @param routerIPs
     */
    public void setRouterIPs(ArrayList<String> routerIPs) {
        this.destinationInfo.setRouterIPs(routerIPs);
    }

    /**
     * Add a router IP address.
     * @param routerIP
     */
    public void addRouterIP(String routerIP) {
        this.destinationInfo.addRouterIP(routerIP);
    }

    /**
     * Get the number of the router IP addresses.
     * @return
     */
    public int getRouterCount() {
        return this.destinationInfo.getRouterCount();
    }

    /**
     * Get the router IP address at the specified index.
     * @param index
     * @return
     */
    public String getRouterIP(int index) {
        return this.destinationInfo.getRouterIP(index);
    }
}

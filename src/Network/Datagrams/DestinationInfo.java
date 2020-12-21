package Network.Datagrams;

import java.util.ArrayList;

/**
 * This class acts as a container for destination information.
 * @author Fernando Villarreal
 * @date 10/24/2020
 */
public class DestinationInfo {

    //============================= CLASS VARIABLES =============================

    private String ipAddress;
    private String portNumber;
    private String linkAddress;
    private ArrayList<String> routerIPs;

    //============================= CONSTRUCTORS =============================

    public DestinationInfo() {
        this.routerIPs = new ArrayList<>();
    }

    public DestinationInfo(String ipAddress, String portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.routerIPs = new ArrayList<>();
    }

    public DestinationInfo(String ipAddress, String portNumber, ArrayList<String> routerIPs) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.routerIPs = routerIPs;
    }

    //============================= METHODS =============================

    /**
     * Get the destination's IP address.
     * @return
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     * Set the destination's IP address.
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Get the destination's port number.
     * @return
     */
    public String getPortNumber() {
        return this.portNumber;
    }

    /**
     * Set the destination's port number.
     * @param portNumber
     */
    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Get the destination's link address.
     * @return
     */
    public String getLinkAddress() {
        return this.linkAddress;
    }

    /**
     * Set the destination's link address.
     * @param linkAddress
     */
    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    /**
     * Get the list router IP addresses.
     * @return
     */
    public ArrayList<String> getRouterIPs() {
        return this.routerIPs;
    }

    /**
     * Set the list of router IP addresses.
     * @param routerIPs
     */
    public void setRouterIPs(ArrayList<String> routerIPs) {
        this.routerIPs = routerIPs;
    }

    /**
     * Add a router IP address.
     * @param routerIP
     */
    public void addRouterIP(String routerIP) {
        this.routerIPs.add(routerIP);
    }

    /**
     * Get the number of the router IP addresses.
     * @return
     */
    public int getRouterCount() {
        return this.routerIPs.size();
    }

    /**
     * Get the router IP address at the specified index.
     * @param index
     * @return
     */
    public String getRouterIP(int index) {
        return this.routerIPs.get(index);
    }
}

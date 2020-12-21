package Network.Layers;

import Network.Datagrams.IPDatagram;
import Network.Datagrams.UserDatagram;
import Network.Utilities;

import java.util.ArrayList;

/**
 * This class acts as the Network Layer. It uses the IPv4 Protocol.
 * @author Fernando Villarreal
 * @date 10/20/2020
 */
public class NetworkLayer {

    //============================= CLASS VARIABLES =============================

    // Default Information for the header
    public static final int versionNum = 4;
    public static final String serviceType = "11000000";
    public static final String fragmentationBits = "00000000000000000000000000000000";
    public static final int timeToLive = 5;
    public static final String protocol = "10110101";
    // Datagrams
    private IPDatagram datagram;
    private UserDatagram data;
    // The host's information
    private final String hostIpAddress;
    private final boolean isRouter;

    //============================= CONSTRUCTORS =============================

    public NetworkLayer(String hostIpAddress, boolean isRouter) {
        this.hostIpAddress = hostIpAddress;
        this.isRouter = isRouter;
    }

    public NetworkLayer(String hostIpAddress, boolean isRouter, UserDatagram data) {
        this.hostIpAddress = hostIpAddress;
        this.isRouter = isRouter;
        this.data = data;
        this.datagram = new IPDatagram(data);
    }

    public NetworkLayer(String hostIpAddress, boolean isRouter, IPDatagram datagram) {
        this.hostIpAddress = hostIpAddress;
        this.isRouter = isRouter;
        this.datagram = datagram;
        this.data = (UserDatagram)datagram.getData();
    }

    //============================= CONSTRUCTORS =============================

    /**
     * Prepare a message for delivery to the mailbox.
     */
    public void prepareMessage() {
        this.attachHeader();
        this.processingModule();
        this.routingModule();
        this.fragmentationModule();
    }

    /**
     * Receive a message from the mailbox.
     */
    public void receiveMessage() {
        this.processingModule();
        this.reassemblyModule();
    }

    /**
     * Attach the header to the IPDatagram.
     */
    private void attachHeader() {
        String header = "";
        String hexLength = this.calcTotalLength();
        header += Utilities.convertIntToHex(NetworkLayer.versionNum);
        header += Utilities.convertIntToHex(datagram.getIPHeaderLength());
        header += Utilities.convertBinToHex(NetworkLayer.serviceType);
        header += hexLength;
        header += NetworkLayer.fragmentationBits;
        header += NetworkLayer.timeToLive;
        header += NetworkLayer.protocol;
        this.datagram.setVersion(NetworkLayer.versionNum);
        this.datagram.setServiceType(NetworkLayer.serviceType);
        this.datagram.setIpTotalLength(Utilities.convertHexToDec(hexLength));
        this.datagram.setIdentification("0000000000000000");
        this.datagram.setFlags("000");
        this.datagram.setFragOffset("0000000000000");
        this.datagram.setTimeToLive(NetworkLayer.timeToLive);
        this.datagram.setProtocol(NetworkLayer.protocol);
        // Calculate the checksum
        int checksum = this.calChecksum();
        header += Utilities.convertIntToHex(checksum);
        this.datagram.setIpHeader(header);
        this.datagram.setNetChecksum(checksum);
    }

    /**
     * Processing module of the IPv4 protocol.
     */
    private void processingModule() {
        // Check if this is the host the datagram is for
        String destinationIP = this.datagram.getDestinationIP();
        if (destinationIP.compareTo(this.hostIpAddress) == 0) {
            // Send datagram to the reassembly module
            return;
        }
        // If this machine is a router, decrement the time-to-live
        if (this.isRouter == true) {
            this.datagram.decrementTimeToLive();
        }
        // If the time-to-live <= 0, discard the datagram.
        if (this.datagram.getTimeToLive() <= 0) {
            // Discard datagram
        }
        // Send datagram to routing module
    }

    private void routingModule() {

    }

    private void fragmentationModule() {

    }

    private void reassemblyModule() {

    }

    /**
     * Calculates and returns the total length of the IPDatagram in hex.
     * @return
     */
    private String calcTotalLength() {
        int headerLength = this.datagram.getIPHeaderLength() * 4;
        int dataLength = this.datagram.getData().getUdpTotalLength();
        int decTotalLength = headerLength + dataLength;
        String hexTotalLength = Utilities.convertIntToHex(decTotalLength);
        return hexTotalLength;
    }

    /**
     * Header checksum calculation for the sender.
     * @return
     */
    private int calChecksum() {
        ArrayList<String> binValues = this.getBinaryValues();
        String totalBinSum = "0";
        for (String binValue : binValues) {
            totalBinSum = Utilities.addBinNums(totalBinSum, binValue);
        }
        totalBinSum = Utilities.complementBinNum(totalBinSum);
        int decChecksum = Utilities.convertBinToDec(totalBinSum);
        return decChecksum;
    }

    /**
     * Get all the values of the IP datagram header in binary format for the checksum calculation.
     * @return
     */
    private ArrayList<String> getBinaryValues() {
        ArrayList<String> binValues = new ArrayList<String>();
        binValues.add(Utilities.convertStringtoBinaryString("" + this.datagram.getVersion()));
        binValues.add(Utilities.convertStringtoBinaryString("" + this.datagram.getIPHeaderLength()));
        binValues.add(Utilities.convertStringtoBinaryString(this.datagram.getServiceType()));
        binValues.add(Utilities.convertStringtoBinaryString("" + this.datagram.getIpTotalLength()));
        binValues.add(Utilities.convertStringtoBinaryString(this.datagram.getIdentification()));
        binValues.add(Utilities.convertStringtoBinaryString(this.datagram.getFlags()));
        binValues.add(Utilities.convertStringtoBinaryString(this.datagram.getFragOffset()));
        binValues.add(Utilities.convertStringtoBinaryString("" + this.datagram.getTimeToLive()));
        binValues.add(Utilities.convertStringtoBinaryString(this.datagram.getProtocol()));
        binValues.add(Utilities.convertStringtoBinaryString(this.datagram.getSourceIP()));
        binValues.add(Utilities.convertStringtoBinaryString(this.datagram.getDestinationIP()));
        return binValues;
    }

    public void setDatagram(IPDatagram datagram) {
        this.datagram = datagram;
        this.data = datagram.getData();
    }

    public void setData(UserDatagram data) {
        this.data = data;
        this.datagram = new IPDatagram(data);
    }

    public IPDatagram getIPDatagram() {
        return this.datagram;
    }

    public UserDatagram getData() {
        return this.data;
    }
}

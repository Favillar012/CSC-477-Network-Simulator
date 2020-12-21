package Network.Layers;

import Network.Datagrams.Datagram;
import Network.Datagrams.UserDatagram;
import Network.Utilities;

import java.util.ArrayList;

/**
 * This class acts as the Transport Layer. It uses the UDP Protocol.
 * @author Fernando Villarreal
 * @date 10/15/2020
 */
public class TransportLayer {

    //============================= CLASS VARIABLESS =============================

    private UserDatagram datagram;
    private Datagram data;

    //============================= CONSTRUCTORS =============================

    public TransportLayer() {}

    public TransportLayer(Datagram data) {
        this.data = data;
        this.datagram = new UserDatagram(data);
    }

    public TransportLayer(UserDatagram datagram) {
        this.datagram = datagram;
        this.data = datagram.getData();
    }

    //============================= METHODS =============================

    /**
     * Prepare the message for to be sent by having run through the IP protocol.
     */
    public void prepareMessage() {
        this.attachHeader();
    }

    /**
     * Receives the message at the Receiver. Returns true if it is accepted, false if rejected.
     * @return boolean
     */
    public boolean receiveMessage() {
        int receivedChecksum = this.datagram.getTransChecksum();
        int checksum = this.calcChecksum(receivedChecksum);
        if (checksum == 0) {
            System.out.println("Checksum = " + checksum + ". Message Accepted.");
            this.detachHeader();
            return true;
        }
        System.out.println("Checksum = " + checksum + ". Message Rejected.");
        return false;
    }

    /**
     * Attaches the UDP header to the message before sending it.
     */
    private void attachHeader() {
        String header = "";
        String sourcePort = this.datagram.getHexSourcePortNumber();
        String destinationPort = this.datagram.getHexDestinationPortNumber();
        String totalLength = this.calcTotalLength();
        int intLength = Integer.parseInt(totalLength, 16);
        int checksum = this.calcChecksum();
        String hexChecksum = Utilities.convertIntToHex(checksum);
        header = sourcePort + destinationPort + totalLength + hexChecksum;
        this.datagram.setTransChecksum(checksum);
        this.datagram.setUdpTotalLength(intLength);
        this.datagram.setUdpHeader(header);
    }

    private void detachHeader() {
        this.data = this.datagram.getData();
    }

    /**
     * Checksum calculation for the Sender.
     * @return decChecksum
     */
    private int calcChecksum() {
        ArrayList<String> binMessage = this.data.getMessage().getBinValues();
        String totalBinSum = "0";
        for (String str : binMessage) {
            totalBinSum = Utilities.addBinNums(totalBinSum, str);
        }
        totalBinSum = Utilities.complementBinNum(totalBinSum);
        int decChecksum = Utilities.convertBinToDec(totalBinSum);
        return decChecksum;
    }

    /**
     * Checksum calculation for the Receiver.
     * @param receivedChecksum
     * @return decChecksum
     */
    private int calcChecksum(int receivedChecksum) {
        ArrayList<String> binMessage = this.data.getMessage().getBinValues();
        String binReceivedChecksum = Utilities.convertIntToBin(receivedChecksum);
        String totalBinSum = "" + binReceivedChecksum;
        for (String str : binMessage) {
            totalBinSum = Utilities.addBinNums(totalBinSum, str);
        }
        totalBinSum = Utilities.complementBinNum(totalBinSum);
        int decChecksum = Utilities.convertBinToDec(totalBinSum);
        return decChecksum;
    }

    /**
     * Calculates and returns the total length of the Datagram in hex.
     * @return String
     */
    private String calcTotalLength() {
        int headerLength = 8; // UDP headers have a fixed size of 8 bytes
        int dataLength = this.data.getMessage().getLength();
        int totalLength = headerLength + dataLength;
        String hexLength = Utilities.convertIntToHex(totalLength);
        return hexLength;
    }

    public void setData(Datagram data) {
        this.data = data;
        this.datagram = new UserDatagram(data);
    }

    public void setUserDatagram(UserDatagram datagram) {
        this.datagram = datagram;
        this.data = datagram.getData();
    }

    public Datagram getData() {
        return this.data;
    }

    public UserDatagram getUserDatagram() {
        return this.datagram;
    }
}

package Network.Layers;

import Network.Datagrams.EthernetFrame;
import Network.Datagrams.IPDatagram;
import Network.Utilities;

/**
 * This class acts as the Data-Link Layer. It uses the Ethernet Protocol.
 * @author Fernando Villarreal
 * @date 11/14/2020
 */
public class DataLinkLayer {

    //============================= CLASS VARIABLES =============================

    // Default Information for the header
    public static final String crc = "00000000000000000000000000000000"; // CRC is not calculated
    public static final String type = "0000000010110101"; // IP protocol
    // IpDatagram and EthernetFrame
    private IPDatagram data;
    private EthernetFrame ethernetFrame;
    // Link Layer Address
    private String linkAddress;

    //============================= CONSTRUCTOR =============================

    public DataLinkLayer(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public DataLinkLayer(String linkAddress, IPDatagram data) {
        this.linkAddress = linkAddress;
        this.data = data;
        this.ethernetFrame = new EthernetFrame(data);
    }

    //============================= METHODS =============================

    /**
     * Prepare a message for delivery to the mailbox.
     */
    public void prepareMessage(IPDatagram ipDatagram) {
        this.ethernetFrame = new EthernetFrame(ipDatagram);
        this.data = ethernetFrame.getData();
        this.attachHeader();
    }

    /**
     * Receive the provided message.
     * @param ethernetFrame
     */
    public void receiveMessage(EthernetFrame ethernetFrame) {
        this.ethernetFrame = ethernetFrame;
        this.data = this.detachHeader(ethernetFrame);
    }

    public IPDatagram detachHeader(EthernetFrame ethernetFrame) {
        IPDatagram data = ethernetFrame.getData();
        return data;
    }

    /**
     * Attach the header with its appropriate fields.
     */
    private void attachHeader() {
        this.ethernetFrame.setSourceLink(this.linkAddress);
        String destinationLink = this.ethernetFrame.getDestinationLink();
        this.ethernetFrame.setDestinationLink(destinationLink);
        this.ethernetFrame.setType(DataLinkLayer.type);
        this.ethernetFrame.setCrc(DataLinkLayer.crc);
        // Create hex string for header
        String hexHeader = "";
        hexHeader += Utilities.convertMessageToHex(this.ethernetFrame.getDestinationLink());
        hexHeader += Utilities.convertMessageToHex(this.ethernetFrame.getSourceLink());
        hexHeader += this.ethernetFrame.getType();
        hexHeader += this.ethernetFrame.getCrc();
        this.ethernetFrame.setFrameHeader(hexHeader);
    }

    public EthernetFrame getEthernetFrame() {
        return this.ethernetFrame;
    }

    public IPDatagram getData() {
        return this.data;
    }
}

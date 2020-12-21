package Network.Datagrams;

import Network.Message;

/**
 * This class implements an Ethernet Frame which is the packet format for data encapsulated
 * by the the Ethernet protocol of the Data-Link Layer.
 * @author Fernando Villarreal
 * @date 11/14/2020
 */
public class EthernetFrame extends IPDatagram {

    //============== CLASS VARIABLES ==============

    // Data-Link Layer Info
    private String type;
    private String crc;
    private String frameHeader;

    // Physical Layer Header Info
    private String preamble;
    private String sdf;

    // Binary Representation
    private String binaryRepresentation;

    //============== CONSTRUCTORS ==============

    public EthernetFrame() {
        super();
    }

    public EthernetFrame(Message message) {
        super(message);
    }

    public EthernetFrame(IPDatagram ipDatagram) {
        super(ipDatagram);
    }

    //============== METHODS ==============

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCrc() {
        return this.crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getFrameHeader() {
        return this.frameHeader;
    }

    public void setFrameHeader(String frameHeader) {
        this.frameHeader = frameHeader;
    }

    public String getPreamble() {
        return this.preamble;
    }

    public void setPreamble(String preamble) {
        this.preamble = preamble;
    }

    public String getSdf() {
        return this.sdf;
    }

    public void setSdf(String sdf) {
        this.sdf = sdf;
    }

    public String getBinaryRepresentation() {
        return this.binaryRepresentation;
    }

    public void setBinaryRepresentation(String binaryRepresentation) {
        this.binaryRepresentation = binaryRepresentation;
    }

    public IPDatagram getData() {
        return new IPDatagram(this);
    }
}

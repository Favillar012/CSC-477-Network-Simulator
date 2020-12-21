package Network.Datagrams;

import Network.Message;

/**
 * IPDatagram is an extension of the base Datagram object class. IPDatagrams are
 * created by the IPv4 Protocol of the Network Layer.
 * @author Fernando Villarreal
 * @date 10/15/2020
 */
public class IPDatagram extends UserDatagram {

    //============== CLASS VARIABLES ==============

    // Header Info
    private int version;
    private int headerLength;
    private String serviceType;
    private int ipTotalLength;
    private String identification;
    private String flags;
    private String fragOffset;
    private int timeToLive;
    private String protocol;
    private int netChecksum;
    private String ipHeader;

    //============== CONSTRUCTORS ==============

    public IPDatagram() {
        super();
    }

    public IPDatagram(Message message) {
        super(message);
        this.headerLength = 5; // Five 4-byte words: 20 bytes.
    }

    public IPDatagram(UserDatagram data) {
        super(data);
        this.headerLength = 5; // Five 4-byte words: 20 bytes.
    }

    public IPDatagram(IPDatagram ipDatagram) {
        super(ipDatagram);
        this.version = ipDatagram.getVersion();
        this.headerLength = ipDatagram.getIPHeaderLength();
        this.serviceType = ipDatagram.getServiceType();
        this.ipTotalLength = ipDatagram.getIpTotalLength();
        this.identification = ipDatagram.getIdentification();
        this.flags = ipDatagram.getFlags();
        this.fragOffset = ipDatagram.getFragOffset();
        this.timeToLive = ipDatagram.getTimeToLive();
        this.protocol = ipDatagram.getProtocol();
        this.netChecksum = ipDatagram.getNetChecksum();
        this.ipHeader = ipDatagram.getIpHeader();
    }

    //============== METHODS ==============

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getIPHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getIpTotalLength() {
        return ipTotalLength;
    }

    public void setIpTotalLength(int ipTotalLength) {
        this.ipTotalLength = ipTotalLength;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getFragOffset() {
        return fragOffset;
    }

    public void setFragOffset(String fragOffset) {
        this.fragOffset = fragOffset;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getNetChecksum() {
        return netChecksum;
    }

    public void setNetChecksum(int netChecksum) {
        this.netChecksum = netChecksum;
    }

    public String getIpHeader() {
        return ipHeader;
    }

    public void setIpHeader(String ipHeader) {
        this.ipHeader = ipHeader;
    }

    public UserDatagram getData() {
        UserDatagram data = new UserDatagram(this);
        return data;
    }

    public void decrementTimeToLive() {
        this.timeToLive--;
    }
}

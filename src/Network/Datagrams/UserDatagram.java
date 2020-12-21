package Network.Datagrams;

import Network.Message;
import Network.Utilities;

/**
 * UserDatagram is an extension of the base Datagram object class. UserDatagrams are
 * created by the UDP Protocol of the Transport Layer.
 * @author Fernando Villarreal
 * @date 10/15/2020
 */
public class UserDatagram extends Datagram {

    //============== CLASS VARIABLES ==============

    // Header Info
    private int udpTotalLength;
    private int transChecksum;
    private String udpHeader;

    //============== CONSTRUCTORS ==============

    public UserDatagram() { super(); }

    public UserDatagram(Message message) {
        super(message);
    }

    public UserDatagram(Datagram data) {
        super(data);
    }

    public UserDatagram(UserDatagram userDatagram) {
        super(userDatagram);
        this.udpTotalLength = userDatagram.getUdpTotalLength();
        this.transChecksum = userDatagram.getTransChecksum();
        this.udpHeader = userDatagram.getUdpHeader();
    }

    //============== METHODS ==============

    public Datagram getData() {
        Datagram data = new Datagram(this);
        return data;
    }

    public int getTransChecksum() {
        return this.transChecksum;
    }

    public String getUdpHeader() {
        return this.udpHeader;
    }

    public String getHexSourcePortNumber() {
        int intPort = Integer.parseInt(this.getSourcePort());
        String hexPort = Utilities.convertIntToHex(intPort);
        return hexPort;
    }

    public String getHexDestinationPortNumber() {
        int intPort = Integer.parseInt(this.getDestinationPort());
        String hexPort = Utilities.convertIntToHex(intPort);
        return hexPort;
    }

    public int getUdpTotalLength() {
        return this.udpTotalLength;
    }

    public void setUdpHeader(String udpHeader) {
        this.udpHeader = udpHeader;
    }

    public void setTransChecksum(int transChecksum) {
        this.transChecksum = transChecksum;
    }

    public void setUdpTotalLength(int udpTotalLength) {
        this.udpTotalLength = udpTotalLength;
    }
}

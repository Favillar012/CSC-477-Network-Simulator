package Network.Layers;

import Network.Datagrams.EthernetFrame;
import Network.Utilities;

import java.util.ArrayList;

/**
 * This class implements the Physical Layer of the TCP/IP Protocol using the
 * Ethernet Protocol.
 * @author Fernando Villarreal
 * @date 11/20/2020
 */
public class PhysicalLayer {

    //==================== CLASS VARIABLES ====================

    private final String preamble;
    private final String sdf;
    private EthernetFrame ethernetFrame;

    //==================== CONSTRUCTOR ====================

    public PhysicalLayer() {
        this.preamble = this.generatePreamble();
        this.sdf = "10101011";
    }

    //==================== METHODS ====================

    /**
     * Prepare the message for delivery.
     * @param ethernetFrame
     */
    public void prepareMessage(EthernetFrame ethernetFrame) {
        this.ethernetFrame = ethernetFrame;
        // Set the Preamble and SDF
        this.ethernetFrame.setPreamble(this.preamble);
        this.ethernetFrame.setSdf(this.sdf);
        // Set the binary string representation
        this.setBinaryRepresentation();
    }

    public EthernetFrame getEthernetFrame() {
        return this.ethernetFrame;
    }

    //=================== PRIVATE METHODS ===================

    /**
     * Creates a binary string that represents the entire EthernetFrame.
     */
    private void setBinaryRepresentation() {
        String binStr = "";
        /* Create the binary representation */
        // Attach the Ethernet Frame fields (Except the CRC)
        binStr += this.ethernetFrame.getPreamble();
        binStr += this.ethernetFrame.getSdf();
        binStr += this.convertLinkAddressToBinaryString(this.ethernetFrame.getDestinationLink());
        binStr += this.convertLinkAddressToBinaryString(this.ethernetFrame.getSourceLink());
        binStr += this.ethernetFrame.getType();
        // Attach the IP Datagram fields
        binStr += Utilities.convertIntToBin(this.ethernetFrame.getVersion());
        binStr += Utilities.convertIntToBin(this.ethernetFrame.getIPHeaderLength());
        binStr += this.ethernetFrame.getServiceType();
        binStr += Utilities.convertIntToBin(this.ethernetFrame.getIpTotalLength());
        binStr += this.ethernetFrame.getIdentification();
        binStr += this.ethernetFrame.getFlags();
        binStr += this.ethernetFrame.getFragOffset();
        binStr += Utilities.convertIntToBin(this.ethernetFrame.getTimeToLive());
        binStr += this.ethernetFrame.getProtocol();
        binStr += Utilities.convertIntToBin(this.ethernetFrame.getNetChecksum());
        binStr += this.convertIpAddressToBinaryString(this.ethernetFrame.getSourceIP());
        binStr += this.convertIpAddressToBinaryString(this.ethernetFrame.getDestinationIP());
        // Attach the User Datagram fields
        binStr += Utilities.convertIntToBin(Integer.parseInt(this.ethernetFrame.getSourcePort()));
        binStr += Utilities.convertIntToBin(Integer.parseInt(this.ethernetFrame.getDestinationPort()));
        binStr += Utilities.convertIntToBin(this.ethernetFrame.getUdpTotalLength());
        binStr += Utilities.convertIntToBin(this.ethernetFrame.getTransChecksum());
        // Attach the binary message
        binStr += this.ethernetFrame.getMessage().getBinMessage();
        // Attach the CRC at the very end
        binStr += this.ethernetFrame.getCrc();
        // Set the binary representation for the EthernetFrame
        this.ethernetFrame.setBinaryRepresentation(binStr);
    }

    /**
     * Generates the Preamble which is simply an alternating 56-bit sequence of 0s and 1s.
     * @return
     */
    private String generatePreamble() {
        int bitCount = 0;
        int curBit = 0;
        String preamble = "";
        while (bitCount < 56) {
            preamble += curBit;
            if (curBit == 0) {
                curBit = 1;
            } else {
                curBit = 0;
            }
            bitCount++;
        }
        return preamble;
    }

    /**
     * Converts the provided IP Address into a binary string.
     * @param ipAddress
     * @return
     */
    private String convertIpAddressToBinaryString(String ipAddress) {
        ArrayList<Integer> splitIpAddress = this.splitIpAddress(ipAddress);
        String binStr = "";
        for (int integer : splitIpAddress) {
            binStr += Utilities.convertIntToBin(integer);
        }
        return binStr;
    }

    /**
     * Converts the provided Link Address into a binary string.
     * @param linkAddress
     * @return
     */
    private String convertLinkAddressToBinaryString(String linkAddress) {
        ArrayList<String> splitLinkAddress = this.splitLinkAddress(linkAddress);
        String binStr = "";
        for (String str : splitLinkAddress) {
            binStr += Utilities.convertHexToBin(str);
        }
        return binStr;
    }

    /**
     * Split the provided IP Address into a list of integers.
     * @param ipAddress
     * @return
     */
    private ArrayList<Integer> splitIpAddress(String ipAddress) {
        ArrayList<Integer> numbers = new ArrayList<>();
        int length = ipAddress.length();
        char dot = '.';
        String curNumStr = "";
        for (int i = 0; i < length; i++) {
            char curChar = ipAddress.charAt(i);
            if (curChar == dot) {
                int curNum = Integer.parseInt(curNumStr);
                numbers.add(curNum);
                curNumStr = "";
            } else {
                curNumStr += curChar;
            }
        }
        // Parse the last number string if it's not empty
        if (!curNumStr.isEmpty()) {
            int lastNum = Integer.parseInt(curNumStr);
            numbers.add(lastNum);
        }
        // Return numbers
        return numbers;
    }

    /**
     * Split the provided link address into a list of hex strings.
     * @param linkAddress
     * @return
     */
    private ArrayList<String> splitLinkAddress(String linkAddress) {
        ArrayList<String> strings = new ArrayList<>();
        int length = linkAddress.length();
        char semicolon = ':';
        String curStr = "";
        for (int i = 0; i < length; i++) {
            char curChar = linkAddress.charAt(i);
            if (curChar == semicolon) {
                strings.add(curStr);
                curStr = "";
            } else {
                curStr += curChar;
            }
        }
        // Add the last string if it's not empty
        if (!curStr.isEmpty()) {
            strings.add(curStr);
        }
        // Returns strings
        return strings;
    }
}

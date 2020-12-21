package GUI;

import Network.Datagrams.Datagram;
import Network.Datagrams.EthernetFrame;
import Network.Datagrams.IPDatagram;
import Network.Datagrams.UserDatagram;

/**
 * This static class stores the copies of all datagrams in circulation throughout the simulation. These are
 * meant for easy access when needed.
 * @author Fernando Villarreal
 * @date 10/24/2020
 */
public class DatagramCopies {

    // Empty Datagram Object
    public static final EthernetFrame emptyDatagram = new EthernetFrame();

    // Host Numbers for identification
    public static final int senderNum = 1;
    public static final int receiverNum = 2;

    // Datagram Numbers for identification
    public static final int datagramANum = 11;
    public static final int datagramBNum = 12;

    //==== Sender's Datagrams ====

    public static Datagram senderBaseDatagramA;
    public static UserDatagram senderTransDatagramA;
    public static IPDatagram senderNetDatagramA;
    public static EthernetFrame senderEthernetFrameA;

    public static Datagram senderBaseDatagramB;
    public static UserDatagram senderTransDatagramB;
    public static IPDatagram senderNetDatagramB;
    public static EthernetFrame senderEthernetFrameB;

    public static Datagram getSenderBaseDatagram(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return senderBaseDatagramA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return senderBaseDatagramB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static UserDatagram getSenderTransDatagram(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return senderTransDatagramA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return senderTransDatagramB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static IPDatagram getSenderNetDatagram(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return senderNetDatagramA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return senderNetDatagramB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static EthernetFrame getSenderEthernetFrame(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return senderEthernetFrameA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return senderEthernetFrameB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static void setSenderDatagrams(EthernetFrame datagramA, EthernetFrame datagramB) {
        // Datagram A
        senderBaseDatagramA = datagramA;
        senderTransDatagramA = datagramA;
        senderNetDatagramA = datagramA;
        senderEthernetFrameA = datagramA;
        // Datagram B
        senderBaseDatagramB = datagramB;
        senderTransDatagramB = datagramB;
        senderNetDatagramB = datagramB;
        senderEthernetFrameB = datagramB;
    }

    //==== Receiver's Datagrams ====

    public static Datagram recBaseDatagramA;
    public static UserDatagram recTransDatagramA;
    public static IPDatagram recNetDatagramA;
    public static EthernetFrame recEthernetFrameA;

    public static Datagram recBaseDatagramB;
    public static UserDatagram recTransDatagramB;
    public static IPDatagram recNetDatagramB;
    public static EthernetFrame recEthernetFrameB;

    public static Datagram getReceiverBaseDatagram(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return recBaseDatagramA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return recBaseDatagramB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static UserDatagram getReceiverTransDatagram(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return recTransDatagramA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return recTransDatagramB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static IPDatagram getReceiverNetDatagram(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return recNetDatagramA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return recNetDatagramB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static EthernetFrame getReceiverEthernetFrame(int datagramNum) {
        if (datagramNum == DatagramCopies.datagramANum) {
            return recEthernetFrameA;
        } else if (datagramNum == DatagramCopies.datagramBNum) {
            return recEthernetFrameB;
        } else {
            return DatagramCopies.emptyDatagram;
        }
    }

    public static void setReceiverDatagrams(EthernetFrame datagramA, EthernetFrame datagramB) {
        // Datagram A
         recBaseDatagramA = datagramA;
         recTransDatagramA = datagramA;
         recNetDatagramA = datagramA;
         recEthernetFrameA = datagramA;
         // Datagram B
         recBaseDatagramB = datagramB;
         recTransDatagramB = datagramB;
         recNetDatagramB = datagramB;
         recEthernetFrameB = datagramB;
    }
}

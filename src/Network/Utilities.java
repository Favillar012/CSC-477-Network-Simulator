package Network;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * This class provides utilities for helping to convert strings into hexadecimal, binary, etc.
 * Date Modified: 9/18/2020
 * @author Fernando Villarreal
 */
public class Utilities {

    //============================= CLASS VARIABLES =============================

    private final static int bin_max_length = 8;
    private final static int hex_max_length = 4;
    private final static int max_num = (int)(Math.pow(2, Utilities.bin_max_length) - 1);
    private final static int min_num = 0;
    private final static char[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    //============================= METHODS =============================

    /**
     * Converts the given String into a CharSequence and returns it.
     * @param str
     * @return
     */
    public CharSequence convertStringIntoCharSequence(String str) {
        int lastIndex = str.length();
        CharSequence chars = str.subSequence(0, lastIndex);
        return chars;
    }

    /**
     * Convert the message, which is a CharSequence, into an array of the ASCII values of each
     * of its characters.
     * @param message
     * @return asciiValue : a byte array.
     */
    public static byte[] convertMessagetoASCIIValues(CharSequence message) {
        String strMessage = message.toString();
        byte[] asciiValues = strMessage.getBytes(StandardCharsets.US_ASCII);
        return asciiValues;
    }

    /**
     * Convert a single character into its ASCII value.
     * @param cha
     * @return byte
     */
    public static byte convertCharToASCIIValue(char cha) {
        return (byte)cha;
    }

    public static String convertIntToHex(int integer) {
        String str = Integer.toHexString(integer);
        str = str.toUpperCase();
        return Utilities.padHexWithZeros(str);
    }

    public static String convertIntToBin(int integer) {
        String str = Integer.toBinaryString(integer);
        return Utilities.padBinWithZeros(str);
    }

    public static int convertBinToDec(String binNum) {
        return Integer.parseInt(binNum, 2);
    }

    public static String convertBinToHex(String binNum) {
        int decNum = Utilities.convertBinToDec(binNum);
        String hexNum = Utilities.convertIntToHex(decNum);
        return hexNum;
    }

    public static int convertHexToDec(String hexNum) {
        int decNum = Integer.parseInt(hexNum, 16);
        return decNum;
    }

    public static String convertHexToBin(String hexNum) {
        int decNum = Utilities.convertHexToDec(hexNum);
        String binStr = Utilities.convertIntToBin(decNum);
        return binStr;
    }

    /**
     * Adds two binary numbers using one's complement arithmetic.
     * @param binNum1
     * @param binNum2
     * @return binResult
     */
    public static String addBinNums(String binNum1, String binNum2) {
        // Convert the binary numbers to decimal and add them
        int decNum1 = Utilities.integerParseInt(binNum1, 2);
        int decNum2 = Utilities.integerParseInt(binNum2, 2);
        int decResult = decNum1 + decNum2;
        // Check if the result is greater than Utilities.max_num
        if (decResult > Utilities.max_num) {
            // Get the extra bits as a separate number
            String binResult = Utilities.convertIntToBin(decResult);
            int numExtraBits = Math.abs(binResult.length() - Utilities.bin_max_length);
            String newBinNum = binResult.substring(0, numExtraBits);
            // Trim the original binResult and add with the newBinNum
            binResult = binResult.substring(numExtraBits);
            return Utilities.addBinNums(newBinNum, binResult);
        }
        // Return the result of the addition in binary
        String binResult = Utilities.convertIntToBin(decResult);
        binResult = Utilities.padBinWithZeros(binResult);
        return binResult;
    }

    /**
     * Complements the given binary number and returns it.
     * @param binNum
     * @return binComplement
     */
    public static String complementBinNum(String binNum) {
        int decNum = Utilities.convertBinToDec(binNum);
        int intComplement = Utilities.max_num - decNum;
        String binComplement = Utilities.convertIntToBin(intComplement);
        binComplement = Utilities.padBinWithZeros(binComplement);
        return binComplement;
    }

    /**
     * Converts the given message into binary form and returns it in an ArrayList<String> where each element
     * is a character from the message in binary ASCII form.
     * @param message
     * @return bits
     */
    public static ArrayList<String> convertMessageToBinary(CharSequence message) {
        // Convert message into its ASCII values
        ArrayList<String> bits = new ArrayList<>();
        byte[] asciiValues = Utilities.convertMessagetoASCIIValues(message);
        // Convert each character from the message into ASCII binary form
        for (byte value : asciiValues) {
            int intValue = (int)value;
            String bitStr = Utilities.convertIntToBin(intValue);
            bitStr = Utilities.padBinWithZeros(bitStr);
            bits.add(bitStr);
        }
        // Return the ArrayList with the bit sequences
        return bits;
    }

    /**
     * Converts the provided string into a binary string.
     * @param str
     * @return
     */
    public static String convertStringtoBinaryString(String str) {
        ArrayList<String> arList = Utilities.convertMessageToBinary(str);
        String binString = "";
        for (String character : arList) {
            binString += character;
        }
        return binString;
    }

    /**
     * Converts the given message into hexadecimal form and returns it in an ArrayList<String> where each element
     * is a character from the message in hexadecimal ASCII form.
     * @param message
     * @return hexes
     */
    public static ArrayList<String> convertMessageToHex(CharSequence message) {
        // Convert message into its ASCII values
        ArrayList<String> hexes = new ArrayList<>();
        byte[] asciiValues = Utilities.convertMessagetoASCIIValues(message);
        // Convert each character from the message into ASCII hexadecimal form
        for (byte value : asciiValues) {
            int intValue = (int)value;
            String hexStr = Utilities.convertIntToHex(intValue);
            hexStr = Utilities.padHexWithZeros(hexStr);
            hexes.add(hexStr);
        }
        // Return the ArrayList with the hex sequences
        return hexes;
    }

    /**
     * Checks if the given character is a digit.
     * @param cha
     * @return boolean
     */
    public static boolean isDigit(char cha) {
        for (int index = 0; index < Utilities.digits.length; index++) {
            if (cha == Utilities.digits[index]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Appends an ArrayList of Strings containing parts of a message into one string and returns it.
     * @param messageParts
     * @return message
     */
    public static String appendMessageArrayList(ArrayList<String> messageParts) {
        String message = "";
        for (String part : messageParts) {
            message += part;
        }
        return message;
    }

    //================= PRIVATE METHODS =================

    /**
     * Adds leading zeros to the given binary number.
     * @param str
     * @return str
     */
    private static String padBinWithZeros(String str) {
        int strLength = str.length();
        int numExtraZeros = Math.abs(Utilities.bin_max_length - strLength);
        for (int i = 0; i < numExtraZeros; i++) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * Adds leading zeros to the given hexadecimal number.
     * @param str
     * @return str
     */
    private static String padHexWithZeros(String str) {
        int strLength = str.length();
        int numExtraZeros = Math.abs(Utilities.hex_max_length - strLength);
        for (int i = 0; i < numExtraZeros; i++) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * Attempt to use Integer.parseInt() to convert a binary number into decimal. If a
     * NumberFormatException gets thrown the provided binary number is too large and must be
     * decreased in size.
     * @param binStr
     * @param radix
     * @return
     */
    private static int integerParseInt(String binStr, int radix) {
        try {
            int result = Integer.parseInt(binStr, radix);
            return result;
        } catch (NumberFormatException ex) {
            // Split the binary string in half
            int midIndex = binStr.length() / 2;
            int strLength = binStr.length();
            String firstHalf = binStr.substring(0, midIndex);
            String secondHalf = binStr.substring(midIndex, strLength);
            // Attempt to parse the binary strings again
            int result1 = Utilities.integerParseInt(firstHalf, 2);
            int result2 = Utilities.integerParseInt(secondHalf, 2);
            // Add the binary strings and return the result in decimal notation
            String binResult1 = Utilities.convertIntToBin(result1);
            String binResult2= Utilities.convertIntToBin(result2);
            String resultBin = Utilities.addBinNums(binResult1, binResult2);
            int decResult = Utilities.convertBinToDec(resultBin);
            return decResult;
        }
    }
}

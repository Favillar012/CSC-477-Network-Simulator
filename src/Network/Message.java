package Network;

import java.util.ArrayList;

/**
 * This class acts as a Message object.
 * Date Modified: 9/15/2020
 * @author Fernando Villarreal
 */
public class Message {

    //============================= CLASS VARIABLES =============================

    private CharSequence message;
    private ArrayList<String> hexValues;
    private ArrayList<String> binValues;

    //============================= CONSTRUCTOR =============================

    public Message(CharSequence message) {
        this.message = message;
        this.hexValues = Utilities.convertMessageToHex(this.message);
        this.binValues = Utilities.convertMessageToBinary(this.message);
    }

    //============================= METHODS =============================

    public CharSequence getMessage() {
        return this.message;
    }

    public ArrayList<String> getHexValues() {
        return this.hexValues;
    }

    public ArrayList<String> getBinValues() {
        return this.binValues;
    }

    public void setMessage(CharSequence message) {
        this.message = message;
        this.hexValues = Utilities.convertMessageToHex(this.message);
        this.binValues = Utilities.convertMessageToBinary(this.message);
    }

    public int getLength() {
        int totalBits = this.binValues.size(); // Each element has 8 bits (1 byte)
        return totalBits;
    }

    public String getBinMessage() {
        return Utilities.appendMessageArrayList(this.binValues);
    }

    public String getHexMessage() {
        return Utilities.appendMessageArrayList(this.hexValues);
    }

    @Override
    public String toString() {
        return this.message.toString();
    }
}

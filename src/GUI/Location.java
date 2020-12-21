package GUI;

/**
 * This class contains the x and y coordinates of this location.
 * @author Fernando Villarreal
 * @date 11/10/2020
 */
public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Location{" + this.x + "," + this.y + "}";
    }
}

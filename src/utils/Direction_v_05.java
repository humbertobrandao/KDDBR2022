package utils;

/**
 *
 * @author humberto
 */
public class Direction_v_05{

    private int deltaX;
    private int deltaY;
    private double distance;
    
    public Direction_v_05(int deltaX, int deltaY, double distance) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.distance = distance;
    }

    /**
     * @return the deltaX
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * @return the deltaY
     */
    public int getDeltaY() {
        return deltaY;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }
    
}
package utils;

/**
 *
 * @author humberto
 */
public class Direction{

    private int deltaX;
    private int deltaY;
    private double distance;
    private double correlation;
    
    public Direction(int deltaX, int deltaY, double distance, double correl) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.distance = distance;
        this.correlation = correl;
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

    /**
     * @return the correlation
     */
    public double getCorrelation() {
        return correlation;
    }
    
}

package hu.innobyte.util;

public class Node {

    private int dist = Integer.MAX_VALUE; // distance from start city

    private Integer previous; // previous city id

    private Integer toFlightId; // fligt id to this city

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public Integer getPrevious() {
        return previous;
    }

    public Integer getToFlightId() {
        return toFlightId;
    }

    public void setValues(int dist, Integer previous, Integer toFlightId) {
        this.dist = dist;
        this.previous = previous;
        this.toFlightId = toFlightId;
    }
}

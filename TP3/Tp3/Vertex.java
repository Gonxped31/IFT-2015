package Tp3;

import java.util.HashMap;
import java.util.Map;

public class Vertex implements Comparable<Vertex> {
    private int key;
    private String value;
    private int distance;
    Map<Integer, String> vertex = new HashMap<>();
    public Vertex(int key, String value){
        this.key = key;
        this.value = value;
        vertex.put(key, value);
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Vertex vertex) {
        return Integer.compare(distance, vertex.distance);
    }
}

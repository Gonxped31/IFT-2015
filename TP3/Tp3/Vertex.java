import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private int key;
    private String value;
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
}

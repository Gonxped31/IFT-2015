package Tp3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Reader {
    private static List<String> names = new ArrayList<>();
    private static Map<String ,String[]> edges;
    private static Map<String ,String> vertices;
    public static void read(String fileName){
        vertices = new TreeMap<>();
        edges = new LinkedHashMap<>();
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while (!(line = reader.readLine()).contains("---")){
                vertices.put(line.trim(), i + "");
                ++i;
            }

            int j = 0;
            while (!(line = reader.readLine()).contains("---")){
                String[] arr = line.split(":");
                arr[0] = arr[0].trim();
                arr[1] = arr[1].split(";")[0].trim();
                String[] edgesArr = arr[1].split(" ");
                edges.put(j + "", edgesArr);
                names.add(arr[0]);
                ++j;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Map<String, String> getVertices() {
        return vertices;
    }

    public static Map<String, String[]> getEdges() {
        return edges;
    }

    public static List<String> getNames() {
        return names;
    }
}

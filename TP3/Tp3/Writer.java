package Tp3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Writer {
    public static void write(String writingFile, List<Edge> result, Map<String, String[]> vertices, int totalWeight){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(writingFile))){
            List<String> verticesName = new ArrayList<>(vertices.keySet());
            for (String str : verticesName) {
                writer.write(str);
                writer.newLine();
            }

            for (Edge edge : result) {
                writer.write(edge.toString());
                writer.newLine();
            }

            writer.write("---" + "\n" + totalWeight + "\n");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

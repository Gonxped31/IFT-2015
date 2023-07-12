package Tp3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.util.*;
import java.util.stream.IntStream;

public class Reader {
    public static List<Map<String, String[]>> read(String fileName){
        List<Map<String, String[]>> result = new ArrayList<>();
        Map<String, String[]> vertices = new LinkedHashMap<>();
        Map<String, String[]> edges = new LinkedHashMap<>();
        int i = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String line;
            while (!(line = bufferedReader.readLine()).contains("---")){
                vertices.put(line.trim(), new String[]{i + ""});
                ++i;
            }

            int j = 0;
            while (!(line = bufferedReader.readLine()).contains("---")){
                String[] arr = line.split(":");
                arr[0] = arr[0].trim();
                arr[1] = arr[1].split(";")[0].trim();
                String[] edgesArr = arr[1].split(" ");
                edges.put(j + "", edgesArr);
                ++j;
            }

            result.add(vertices);
            result.add(edges);

        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}

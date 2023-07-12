import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Reader {
    private static List<String> names = new ArrayList<>();
    public static List<Map<String, String[]>> read(String fileName){
        List<Map<String, String[]>> result = new ArrayList<>();
        Map<String, String[]> vertices = new LinkedHashMap<>();
        Map<String, String[]> edges = new LinkedHashMap<>();
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while (!(line = reader.readLine()).contains("---")){
                vertices.put(line.trim(), new String[]{i + ""});
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

            result.add(vertices);
            result.add(edges);

        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> getNames() {
        return names;
    }
}

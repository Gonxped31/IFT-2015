package Tp2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {

    public static ArrayList<ArrayList<String>> read(String fileName, int startLine) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int actualLine = 1;
            while (actualLine != startLine) {
                reader.readLine();
                actualLine++;
            }
            result = reader(fileName, reader);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static ArrayList<ArrayList<String>> reader(String fileName, BufferedReader reader) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try {
            String line;
            boolean bool = true;
            while(bool) {
                if ((line = reader.readLine()) != null){
                    if(!line.contains(";")){
                        String[] words = line.trim().split(":")[0].replaceAll("\\s+",",").split(",");
                        ArrayList<String> trimmedWords = new ArrayList<>();
                        for (int i = 0; i < words.length; i++) {
                            trimmedWords.add(words[i].trim());
                        }
                        result.add(trimmedWords);
                    } else if (removeSpace(line).length != 1){
                        String[] words = line.trim().split(";")[0].replaceAll("\\s+",",").split(",");
                        ArrayList<String> trimmedWords = new ArrayList<>();
                        for (int i = 0; i < words.length; i++) {
                            trimmedWords.add(words[i].trim());
                        }
                        result.add(trimmedWords);
                        bool = false;
                    } else {
                        bool = false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String[] removeSpace(String string) {
        String[] words = string.trim().split(" ");
        String[] array = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            array[i] += words[i].trim();
        }
        return array;
    }

}
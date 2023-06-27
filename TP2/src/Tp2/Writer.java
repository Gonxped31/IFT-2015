package Tp2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {

    public static void write(String fileName, int startRow, ArrayList<String> textToWrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String string : textToWrite) {
                writer.append(string);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
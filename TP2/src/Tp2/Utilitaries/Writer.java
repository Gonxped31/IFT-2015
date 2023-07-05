package Tp2.Utilitaries;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Writer {
    public static void write(String fileName, int startLine, List<String> textToWrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            int currentLine = 1;

            for (int i = 1; i < startLine; i++) {
                writer.append("");
            }

            for (String string : textToWrite) {
                writer.append(string);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

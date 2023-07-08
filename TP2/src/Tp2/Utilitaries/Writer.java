package Tp2.Utilitaries;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Writer {
    public static void write(String fileName, List<String> textToWrite) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
            randomAccessFile.seek(randomAccessFile.length());
            textToWrite.forEach(text -> {
                try {
                    randomAccessFile.writeBytes("\n" + text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

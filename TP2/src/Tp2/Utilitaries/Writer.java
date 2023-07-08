package Tp2.Utilitaries;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Writer {
    public static void write(String fileName, List<String> textToWrite) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
            randomAccessFile.seek(randomAccessFile.length());
            if (textToWrite.size() > 1){
                textToWrite.forEach(text -> {
                    try {
                        randomAccessFile.writeBytes(text + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                randomAccessFile.writeBytes("\n");
                randomAccessFile.close();
            } else if (textToWrite.size() == 1 && textToWrite.get(0).contains("APPROV")){
                textToWrite.forEach(text -> {
                    try {
                        randomAccessFile.writeBytes(text + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                randomAccessFile.close();
            } else {
                textToWrite.forEach(text -> {
                    try {
                        randomAccessFile.writeBytes(text + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                randomAccessFile.writeBytes("\n");
                randomAccessFile.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

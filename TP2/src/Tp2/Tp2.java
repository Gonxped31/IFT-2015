package Tp2;
import Tp2.Utilitaries.Manager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Tp2 {
    public static void main(String[] args) {
        String input = "C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP2\\src\\Tp2\\tests\\exemple1.txt";
        String output = "C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP2\\src\\Tp2\\output.txt";
        Manager manager = new Manager();
        manager.manager(input, output);
    }

}
package Tp2;

import Tp2.Utilitaries.Manager;

public class Tp2 {
    public static void main(String[] args) {
        String input = "C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP2\\src\\Tp2\\tests\\exemple7.txt";
        String output = "C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP2\\src\\Tp2\\output.txt";
        Manager manager = new Manager();
        manager.manager(input, output);
        /*Manager manager = new Manager();
        manager.manager(args[0], args[1]);*/
    }
}
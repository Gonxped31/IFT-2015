package Tp2;
import java.util.ArrayList;

public class Tp2 {
    private static int actualReaderRow;
    private static int actualWriterRow;
    private static Stock stock = new Stock();
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP2\\src\\Tp2\\exemple1.txt";
        actualReaderRow = 1;
        manager(inputFile);
    }

    public static void manager(String inputFile) {
        ArrayList<ArrayList<String>> result;
        while ((result = Reader.read(inputFile, actualReaderRow)).size() != 0) {
            //System.out.println(result);
            actualReaderRow += result.size() + 1;
            switch (result.get(0).get(0)) {
                case "DATE" -> date();
                case "APPROV" -> supply(result, inputFile);
                case "PRESCRIPTION" -> prescription();
                case "STOCK" -> displayStock();
            }
        }
        System.out.println("Done.");
    }

    public static void supply(ArrayList<ArrayList<String>> result, String inputFile) {
        /*System.out.println("SUPPLY !");
        System.out.println(result);
        ArrayList<Drug> drugs = new ArrayList<>();
        for (int i = 1; i < result.size(); i++) {
            ArrayList<String> drug = result.get(i);
            drugs.add(new Drug(drug.get(0), Integer.parseInt(drug.get(1)), drug.get(2)));
        }
        stock.addDrugs(drugs);*/
    }

    public static void action() {
        // TODO
    }

    public static void displayStock() {
        System.out.println("STOCK");
        // TODO
    }

    public static void prescription() {
        System.out.println("PRESC");
        // TODO
    }

    public static void drugAvailability(){
        // TODO
    }

    public static void date(){
        System.out.println("Date");
        // TODO
    }

}
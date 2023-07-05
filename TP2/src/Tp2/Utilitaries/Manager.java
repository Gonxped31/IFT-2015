package Tp2.Utilitaries;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private int actualReaderLine = 1;
    private int actualWriterLine = 1;
    private final Reader reader = new Reader();
    private final Writer writer = new Writer();
    private final Drug drugConvertor = new Drug();
    private Stock stock = new Stock();

    //TODO: Put a global variable "current date"

    public void manager(String readingFile, String writingFile){
        List<List<String>> result;
        while ((result = reader.read(readingFile, actualReaderLine)).size() != 0 && !result.contains(null)){
            actualReaderLine += result.size();
            System.out.println(result);
            switch (result.get(0).get(0)){
                case "DATE" -> {
                    //System.out.println("DATE");
                    dateManager();
                }
                case "PRESCRIPTION" -> {
                    //System.out.println("PRESCRIPTION");
                    prescriptionManager();
                }
                case "APPROV" -> {
                    //System.out.println("APPROV");
                    supplyManager(result);
                }
                case "STOCK" -> {
                    //System.out.println("STOCK");
                    stockManager();
                }
            }
        }
    }

    public void dateManager(){
        //TODO
    }

    public void prescriptionManager(){
        //TODO
    }

    public void supplyManager(List<List<String>> drugs){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Drug> drugsToAdd = convertToDrug(drugs);
        stock.add(drugsToAdd);
        /*List<String> text = new ArrayList<>();
        text.add("APPROV OK");
        Writer.write("C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP2\\src\\Tp2\\output.txt", actualWriterLine, text);
        actualWriterLine += text.size();*/
    }

    public List<Drug> convertToDrug(List<List<String>> stringDrugs){
        List<Drug> drugs = new ArrayList<>();
        for (List<String> drug : stringDrugs) {
            drugs.add(drugConvertor.parseDrug(drug));
        }
        return drugs;
    }

    public void stockManager(){
        //TODO
        for (String drug : stock.getFormatedStock("2023/07/05")) {
            System.out.println(drug);
        }
    }

}

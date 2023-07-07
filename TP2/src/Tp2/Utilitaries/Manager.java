package Tp2.Utilitaries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {
    private int actualReaderLine = 1;
    private int actualWriterLine = 1;
    private final Reader reader = new Reader();
    private final Writer writer = new Writer();
    private final Drug drugConvertor = new Drug();
    private final Prescription prescriptionConvertor = new Prescription();
    private Stock stock = new Stock();
    private String currentDate;
    private int i = 1;


    public void manager(String readingFile, String writingFile){
        List<List<String>> result;
        while ((result = reader.read(readingFile, actualReaderLine)).size() != 0 && !result.contains(null)){
            actualReaderLine += result.size();
            //System.out.println(result);
            switch (result.get(0).get(0)){
                case "DATE" -> dateManager(result);
                case "PRESCRIPTION" -> prescriptionManager(result);
                case "APPROV" -> supplyManager(result);
                case "STOCK" -> stockManager();
            }
        }
    }

    public void dateManager(List<List<String>> dates){
        String date = dates.get(0).get(1);
        if(DateTools.isValid(date)){
            this.currentDate = date;
            List<String> orders = stock.getOrders();
            if (orders.size() > 0){
                System.out.println(date + " COMMANDES :");
                for (String order : orders) {
                    System.out.println(order);
                }
                stock.emptyOrder();
            } else {
                System.out.println(date + " OK");
            }
        } else{
            //TODO
        }
        System.out.println(" ");
    }

    public void prescriptionManager(List<List<String>> drugs){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Prescription> prescriptions = convertToPrescription(drugs);
        stock.update(prescriptions);
        List<String> listPrescriptions = stock.getPrescriptions();
        System.out.println("PRESCRIPTION " + i);
        for (String prescription : listPrescriptions) {
            System.out.println(prescription);
        }
        stock.emptyPrescriptions();
        System.out.println(" ");
        ++i;
        //TODO
    }

    public void supplyManager(List<List<String>> drugs){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Drug> drugsToAdd = convertToDrug(drugs);
        stock.add(drugsToAdd);
        System.out.println("APPROV OK");
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

    public List<Prescription> convertToPrescription(List<List<String>> stringDrugs){
        List<Prescription> prescriptions = new ArrayList<>();
        for (List<String> drug : stringDrugs) {
            prescriptions.add(prescriptionConvertor.parsePrescription(drug));
        }
        return prescriptions;
    }

    public void stockManager(){
        //TODO
        for (String drug : stock.getFormatedStock(currentDate)) {
            System.out.println(drug);
        }
        System.out.println(" ");
    }

}

package Tp2.Utilitaries;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {
    private int actualReaderLine = 1;
    private final Reader reader = new Reader();
    private final Writer writer = new Writer();
    private final Drug drugConvertor = new Drug();
    private final Prescription prescriptionConvertor = new Prescription();
    private final Stock stock = new Stock();
    private String currentDate;
    private int i = 1;


    public void manager(String readingFile, String writingFile){
        List<List<String>> result;
        while ((result = reader.read(readingFile, actualReaderLine)).size() != 0 && !result.contains(null)){
            actualReaderLine += result.size();
            switch (result.get(0).get(0)){
                case "DATE" -> dateManager(result, writingFile);
                case "PRESCRIPTION" -> prescriptionManager(result, writingFile);
                case "APPROV" -> supplyManager(result, writingFile);
                case "STOCK" -> stockManager(writingFile);
            }
        }
    }

    public void dateManager(List<List<String>> dates, String writingFile){
        String date = dates.get(0).get(1);
        if(DateTools.isValid(date)){
            this.currentDate = date;
            List<String> orders = stock.getOrders();
            if (orders.size() > 0){
                orders.add(0,date + " COMMANDES :");
                Writer.write(writingFile, orders);
                stock.emptyOrder();
            } else {
                orders.add(date + " OK");
                Writer.write(writingFile, orders);
            }
        }
    }

    public void prescriptionManager(List<List<String>> drugs, String writingFile){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Prescription> prescriptions = convertToPrescription(drugs);
        stock.update(prescriptions, currentDate);
        List<String> listPrescriptions = stock.getPrescriptions();
        listPrescriptions.add(0, "PRESCRIPTION " + i);
        Writer.write(writingFile, listPrescriptions);
        stock.emptyPrescriptions();
        ++i;
    }

    public void supplyManager(List<List<String>> drugs, String writingFile){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Drug> drugsToAdd = convertToDrug(drugs);
        stock.add(drugsToAdd);
        List<String> text = new ArrayList<>();
        text.add("APPROV OK");
        Writer.write(writingFile, text);
    }

    public List<Drug> convertToDrug(List<List<String>> stringDrugs){
        List<Drug> drugs = new ArrayList<>();
        stringDrugs.forEach(drug -> drugs.add(drugConvertor.parseDrug(drug)));
        return drugs;
    }

    public List<Prescription> convertToPrescription(List<List<String>> stringDrugs){
        List<Prescription> prescriptions = new ArrayList<>();
        stringDrugs.forEach(drug -> prescriptions.add(prescriptionConvertor.parsePrescription(drug)));
        return prescriptions;
    }

    public void stockManager(String writingFile){
        stock.removeExpiredDrugs(currentDate);
        List<String> text = stock.getFormatedStock(currentDate);
        Writer.write(writingFile, text);
    }

}

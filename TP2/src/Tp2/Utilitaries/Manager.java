package Tp2.Utilitaries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Manager {
    private int actualReaderLine = 1;
    private final Reader reader = new Reader();
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

    private void dateManager(List<List<String>> dates, String writingFile){
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

    private void prescriptionManager(List<List<String>> drugs, String writingFile){
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

    private void supplyManager(List<List<String>> drugs, String writingFile){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Drug> drugsToAdd = convertToDrug(drugs);
        stock.add(drugsToAdd);
        List<String> text = new ArrayList<>();
        text.add("APPROV OK");
        Writer.write(writingFile, text);
    }

    private List<Drug> convertToDrug(List<List<String>> stringDrugs){
        return stringDrugs.stream()
                .map(drugConvertor::parseDrug)
                .collect(Collectors.toList());
    }

    private List<Prescription> convertToPrescription(List<List<String>> stringDrugs){
        return stringDrugs.stream()
                .map(prescriptionConvertor::parsePrescription)
                .collect(Collectors.toList());
    }

    private void stockManager(String writingFile){
        stock.removeExpiredDrugs(currentDate);
        List<String> text = stock.getFormatedStock(currentDate);
        Writer.write(writingFile, text);
    }

}

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Manager {
    private int actualReaderLine = 1;
    private final Reader reader = new Reader();
    private final Medication medicationConvertor = new Medication();
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

    // Complexity : O(n)
    private void dateManager(List<List<String>> dates, String writingFile){
        String date = dates.get(0).get(1);
        if(DateTools.isValid(date)){
            this.currentDate = date;
            List<String> orders = stock.getOrders(); //O(n)
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

    // Complexity : O(m(n log n))
    private void prescriptionManager(List<List<String>> drugs, String writingFile){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Prescription> prescriptions = convertToPrescription(drugs); //O(m)
        stock.checkPrescriptions(prescriptions, currentDate);  //O(m(n log n))
        List<String> listPrescriptions = stock.getPrescriptions();
        listPrescriptions.add(0, "PRESCRIPTION " + i);
        Writer.write(writingFile, listPrescriptions);
        stock.emptyPrescriptions();
        ++i;
    }

    // Complexity : O(n log n)
    private void supplyManager(List<List<String>> drugs, String writingFile){
        drugs.remove(0);
        int last = drugs.size() - 1;
        drugs.remove(last);
        List<Medication> drugsToAdd = convertToDrug(drugs);
        stock.add(drugsToAdd); //O(n log n)
        List<String> text = new ArrayList<>();
        text.add("APPROV OK");
        Writer.write(writingFile, text);
    }

    private List<Medication> convertToDrug(List<List<String>> stringDrugs){
        return stringDrugs.stream()
                .map(medicationConvertor::parseDrug)
                .collect(Collectors.toList());
    }

    // Complexity : O(m)
    private List<Prescription> convertToPrescription(List<List<String>> stringDrugs){
        return stringDrugs.stream()
                .map(prescriptionConvertor::parsePrescription)
                .collect(Collectors.toList());
    }

    private void stockManager(String writingFile){
        stock.removeExpiredMedications(currentDate);
        List<String> text = stock.getFormatedStock(currentDate);
        Writer.write(writingFile, text);
    }

}

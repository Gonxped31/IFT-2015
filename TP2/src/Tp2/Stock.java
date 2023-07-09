import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.SortedMap;

public class Stock {
    private final TreeMap<String, TreeMap<String, Medication>> stock = new TreeMap<>();
    private final TreeMap<String, String> orders = new TreeMap<>();
    private final List<String> prescriptionStringList = new ArrayList<>();

    // Complexity : O(n log n)
    public void add(List<Medication> medications){
        medications.forEach(medication -> {   //O(n)
            if (stock.containsKey(medication.getName())){ //O(log n)
                updateQuantity(medication);   //O(log n)
            } else {
                TreeMap<String, Medication> medicationTree = new TreeMap<>();
                medicationTree.put(medication.getExpirationDate(), medication);
                stock.put(medication.getName(), medicationTree);
            }
        });
    }

    // Complexity : O(log n)
    public void updateQuantity(Medication newMedication){
        TreeMap<String, Medication> tree = stock.get(newMedication.getName()); //O(log n)
        if (tree.containsKey(newMedication.getExpirationDate())){       //O(log n)
            Medication medication = tree.get(newMedication.getExpirationDate()); //O(log n)
            int newQuantity = Integer.parseInt(medication.getQuantity()) + Integer.parseInt(newMedication.getQuantity()); //O(1)
            medication.setQuantity(String.valueOf(newQuantity));    //O(1)
        } else {
            tree.put(newMedication.getExpirationDate(), newMedication); //O(log n)
            stock.put(newMedication.getName(), tree);  //O(log n)
        }
    }

    // Complexity : O(m(n log n))
    public void checkPrescriptions(List<Prescription> prescriptions, String date){
        LocalDate currentDate = LocalDate.parse(date);  //O(1)
        prescriptions.forEach(prescription -> {    //O(m)
            String prescriptionName = prescription.getName();  //O(1)
            LocalDate minDate = currentDate.plusDays(prescription.getQuantity()); //O(1)
            if (stock.containsKey(prescriptionName)){
                if (validatePrescription(prescription, minDate)){  //O(n log n)
                    prescription.setStatus("OK");
                    prescriptionStringList.add(prescription.parseString());  //O(1)
                } else {
                    updateOrder(prescription); //O(log k)
                }
            } else {
                updateOrder(prescription); //O(log k)
            }
        });
    }

    // Complexity : O(n log n)
    public boolean validatePrescription(Prescription prescription, LocalDate minDate){
        String prescriptionName = prescription.getName();  //O(1)
        TreeMap<String, Medication> treeMapList = stock.get(prescriptionName);  //O(log n)
        SortedMap<String, Medication> subMap = treeMapList.tailMap(minDate.toString());   //O(log n)
        return subMap.entrySet().stream().anyMatch(entry -> {   //O(n)
            Medication medication = entry.getValue();  //O(1)
            int newQuantity = Integer.parseInt(medication.getQuantity()) - prescription.getQuantity();  //O(1)
            if (newQuantity > 0){   //O(1)
                medication.setQuantity(String.valueOf(newQuantity)); //O(1)
                return true;   //O(1)
            } else if (newQuantity == 0) {    //O(1)
                removeFromStock(medication);  //O(log n)
                return true;   //O(1)
            }
            return false;
        });
    }

    // Complexity : O(log n)
    public void removeFromStock(Medication medication){
        stock.get(medication.getName()).remove(medication.getExpirationDate(), medication); //O(log n)
    }

    //Complexity : O(log k)
    private void updateOrder(Prescription prescription) {
        String prescriptionName = prescription.getName(); //O(1)
        prescription.setStatus("COMMANDE");   //O(1)
        prescriptionStringList.add(prescription.parseString());  //O(1)
        if (!orders.containsKey(prescriptionName)){  //O(log k)
            orders.put(prescriptionName ,prescription.parseDateCommandStringFormat().trim());  //O(log k)
        } else {
            String medication1 = orders.get(prescriptionName);  //O(log k)
            String[] splittedMedication = medication1.split(" ");  //O(1)
            splittedMedication[1] = String.valueOf(Integer.parseInt(splittedMedication[1]) + prescription.getQuantity()); //O(1)
            String newMedication = String.join(" ", splittedMedication);  //O(1)
            orders.put(prescriptionName, newMedication.trim()); //O(log k)
        }
    }

    public List<String> getFormatedStock(String currentDate){
        List<String> list = new ArrayList<>();
        list.add("STOCK " + currentDate);
        List<TreeMap<String, Medication>> treeMapList = new ArrayList<>(stock.values());
        List<Medication> medications = new ArrayList<>();
        treeMapList.forEach(stringMedicationTreeMap -> {
            List<Medication> tmpList = new ArrayList<>(stringMedicationTreeMap.values());
            medications.addAll(tmpList);
        });
        medications.forEach(medication -> list.add(medication.parseString()));
        return list;
    }

    // Complexity : O(n)
    public List<String> getOrders(){
        return new ArrayList<>(orders.values());
    }

    // Complexity : O(1)
    public void emptyOrder() {
        orders.clear();
    }

    public void emptyPrescriptions() {
        prescriptionStringList.clear();
    }

    public List<String> getPrescriptions(){
        return prescriptionStringList;
    }


    // Complexity : O(n*m)
    public void removeExpiredMedications(String currentDate1) {
        LocalDate currentDate = LocalDate.parse(currentDate1);
        stock.keySet().forEach(name -> {    //O(n)
            TreeMap<String, Medication> treeMap = stock.get(name);
            if (!treeMap.isEmpty()) {
                new ArrayList<>(treeMap.keySet()).forEach(stringDate -> { //O(m)
                    LocalDate date = LocalDate.parse(stringDate);
                    if (date.isBefore(currentDate) || date.isEqual(currentDate)) {
                        treeMap.remove(stringDate); //O(log n)
                    }
                });
            }
        });
    }
}

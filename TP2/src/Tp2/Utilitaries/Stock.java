package Tp2.Utilitaries;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Stock {
    private final TreeMap<String, TreeMap<String, Drug>> stock = new TreeMap<>();
    private final TreeMap<String, String> orders = new TreeMap<>();
    private final List<String> prescriptionStringList = new ArrayList<>();

    public void add(List<Drug> drugs){
        drugs.forEach(drug -> {
            if (stock.containsKey(drug.getName())){
                updateQuantity(drug);
            } else {
                TreeMap<String, Drug> drugTree = new TreeMap<>();
                drugTree.put(drug.getExpirationDate(), drug);
                stock.put(drug.getName(), drugTree);
            }
        });
    }

    public void updateQuantity(Drug newDrug){
        TreeMap<String, Drug> tree = stock.get(newDrug.getName());
        if (tree.containsKey(newDrug.getExpirationDate())){
            Drug drug = tree.get(newDrug.getExpirationDate());
            int newQuantity = Integer.parseInt(drug.getQuantity()) + Integer.parseInt(newDrug.getQuantity());
            drug.setQuantity(String.valueOf(newQuantity));
        } else {
            tree.put(newDrug.getExpirationDate(), newDrug);
            stock.put(newDrug.getName(), tree);
        }
    }

    public void update(List<Prescription> prescriptions, String date){
        LocalDate currentDate = LocalDate.parse(date);
        prescriptions.forEach(prescription -> {
            String prescriptionName = prescription.getName();
            LocalDate minDate = currentDate.plusDays(prescription.getQuantity());
            if (stock.containsKey(prescriptionName)){
                if (validatePrescription(prescription, minDate)){
                    prescription.setStatus("OK");
                    prescriptionStringList.add(prescription.parseString());
                } else {
                    updateOrder(prescription);
                }
            } else {
                updateOrder(prescription);
            }
        });
    }

    public boolean validatePrescription(Prescription prescription, LocalDate minDate){
        String prescriptionName = prescription.getName();
        TreeMap<String, Drug> treeMapList = stock.get(prescriptionName);
        SortedMap<String, Drug> subMap = treeMapList.tailMap(minDate.toString());
        return subMap.entrySet().stream().anyMatch(entry -> {
            Drug drug = entry.getValue();
            int newQuantity = Integer.parseInt(drug.getQuantity()) - prescription.getQuantity();
            if (newQuantity > 0){
                drug.setQuantity(String.valueOf(newQuantity));
                return true;
            } else if (newQuantity == 0) {
                removeFromStock(drug);
                return true;
            }
            return false;
        });
    }

    public void removeFromStock(Drug drug){
        stock.get(drug.getName()).remove(drug.getExpirationDate(), drug);
    }

    private void updateOrder(Prescription prescription) {
        String prescriptionName = prescription.getName();
        prescription.setStatus("COMMANDE");
        prescriptionStringList.add(prescription.parseString());
        if (!orders.containsKey(prescriptionName)){
            orders.put(prescriptionName ,prescription.parseDateCommandStringFormat().trim());
        } else {
            String drug1 = orders.get(prescriptionName);
            String[] splittedDrug = drug1.split(" ");
            splittedDrug[1] = String.valueOf(Integer.parseInt(splittedDrug[1]) + prescription.getQuantity());
            String newDrug = String.join(" ", splittedDrug);
            orders.put(prescriptionName, newDrug.trim());
        }
    }

    public List<String> getFormatedStock(String currentDate){
        List<String> list = new ArrayList<>();
        list.add("STOCK " + currentDate);
        List<TreeMap<String, Drug>> treeMapList = new ArrayList<>(stock.values());
        List<Drug> drugs = new ArrayList<>();
        treeMapList.forEach(stringDrugTreeMap -> {
            List<Drug> tmpList = new ArrayList<>(stringDrugTreeMap.values());
            drugs.addAll(tmpList);
        });
        drugs.forEach(drug -> list.add(drug.parseString()));
        return list;
    }

    public List<String> getOrders(){
        return new ArrayList<>(orders.values());
    }

    public void emptyOrder() {
        orders.clear();
    }

    public void emptyPrescriptions() {
        prescriptionStringList.clear();
    }

    public List<String> getPrescriptions(){
        return prescriptionStringList;
    }

    public void removeExpiredDrugs(String currentDate1) {
        LocalDate currentDate = LocalDate.parse(currentDate1);
        stock.keySet().forEach(name -> {
            TreeMap<String, Drug> treeMap = stock.get(name);
            if (!treeMap.isEmpty()) {
                new ArrayList<>(treeMap.keySet()).forEach(stringDate -> {
                    LocalDate date = LocalDate.parse(stringDate);
                    if (date.isBefore(currentDate) || date.isEqual(currentDate)) {
                        treeMap.remove(stringDate);
                    }
                });
            }
        });
    }
}

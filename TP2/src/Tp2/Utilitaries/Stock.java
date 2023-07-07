package Tp2.Utilitaries;

import com.sun.jdi.request.StepRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class Stock {
    private TreeMap<String, TreeMap<String, Drug>> stock = new TreeMap<>();
    private TreeMap<String, String> orders = new TreeMap<>();
    private List<String> prescriptionStringList = new ArrayList<>();

    public void add(List<Drug> drugs){
        for (Drug drug : drugs) {
            if (stock.containsKey(drug.getName())){
                updateQuantity(drug);
            } else {
                TreeMap<String, Drug> drugTree = new TreeMap<>();
                drugTree.put(drug.getExpirationDate(), drug);
                stock.put(drug.getName(), drugTree);
            }
        }
    }

    public void updateQuantity(Drug newDrug){
        TreeMap<String, Drug> tree = stock.get(newDrug.getName());
        if (tree.containsKey(newDrug.getExpirationDate())){
            Drug drug = tree.get(newDrug.getExpirationDate());
            int newQuantity = Integer.parseInt(drug.getQuantity()) + Integer.parseInt(newDrug.getQuantity());
            drug.setQuantity(newQuantity + "");
        } else {
            tree.put(newDrug.getExpirationDate(), newDrug);
            stock.put(newDrug.getName(), tree);
        }
    }

    public void update(List<Prescription> prescriptions){
        //TODO: verifier expiration et (date actuelle + repetition)
        //TODO: trouver un médicament qui respecte les critères (pas juste le premier)
        for (Prescription prescription : prescriptions) {
            String prescriptionName = prescription.getName();
            if (stock.containsKey(prescriptionName)){
                String key = stock.get(prescriptionName).firstEntry().getKey();
                Drug drug = stock.get(prescriptionName).get(key);
                int quantity =  Integer.parseInt(drug.getQuantity()) - prescription.getQuantity();
                if (quantity > 0){
                    drug.setQuantity(quantity + "");
                    prescription.setStatus("OK");
                    prescriptionStringList.add(prescription.parseString());
                } else if (quantity == 0) {
                    stock.get(prescriptionName).remove(key, drug);
                    prescription.setStatus("OK");
                    prescriptionStringList.add(prescription.parseString());
                } else {
                    updateOrder(prescription, prescriptionName);
                }
            } else {
                updateOrder(prescription, prescriptionName);
            }
        }
    }

    private void updateOrder(Prescription prescription, String prescriptionName) {
        prescription.setStatus("COMMANDE");
        prescriptionStringList.add(prescription.parseString());
        if (!orders.containsKey(prescriptionName)){
            orders.put(prescriptionName ,prescription.parseDateCommandStringFormat());
        } else {
            String drug1 = orders.get(prescriptionName);
            String[] splittedDrug = drug1.split("\t");
            splittedDrug[1] = Integer.parseInt(splittedDrug[1]) + prescription.getQuantity() + "";
            String newDrug = "";
            for (String str : splittedDrug) {
                newDrug += str + "\t";
            }
            orders.put(prescriptionName, newDrug);
        }
    }


    public List<String> getFormatedStock(String currentDate){
        List<String> list = new ArrayList<>();
        list.add("STOCK " + currentDate);
        List<TreeMap<String, Drug>> treeMapList = new ArrayList<>(stock.values());
        List<Drug> drugs = new ArrayList<>();
        for (int i = 0; i < treeMapList.size(); i++) {
            List<Drug> tmpList = new ArrayList<>(treeMapList.get(i).values());
            for (int j = 0; j < tmpList.size(); j++) {
                drugs.add(tmpList.get(j));
            }
        }
        for (Drug drug: drugs) {
            list.add(drug.parseString());
        }
        return list;
    }

    public List<String> getOrders(){
        List<String> stringOrders = new ArrayList<>(orders.values());
        return stringOrders;
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

}

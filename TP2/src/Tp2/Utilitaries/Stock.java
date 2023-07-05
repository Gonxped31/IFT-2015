package Tp2.Utilitaries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class Stock {
    TreeMap<String, List<Drug>> stock = new TreeMap<>();
    List<Drug> stockList = new ArrayList<>();

    //Method for supply.
    public void add(List<Drug> drugs){
        addToStockList(drugs);
        for (Drug drug : drugs) {
            List<Drug> newDrugs = new ArrayList<>();
            String key = drug.getExpirationDate();
            if (stock.containsKey(key)) {
                List<Drug> list = stock.get(key);
                int index = findDrug(drug, list);
                if (index != -1){
                    int oldQuant = Integer.parseInt(list.get(index).getQuantity());
                    int newQuant = oldQuant + Integer.parseInt(drug.getQuantity());
                    list.get(index).setQuantity(newQuant + "");
                } else {
                    list.add(drug);
                    stock.put(key, list);
                }
            } else {
                newDrugs.add(drug);
                stock.put(drug.getExpirationDate(), newDrugs);
            }
        }
    }


    private void addToStockList(List<Drug> drugs){
        int i = 0;
        for (Drug drug : drugs) {
            for (Drug drug1 : stockList){
                if (drug.getName().equals(drug1.getName()) && drug.getExpirationDate().equals(drug1.getExpirationDate())) {
                    int oldQuant = Integer.parseInt(drug1.getQuantity());
                    int newQuant = oldQuant + Integer.parseInt(drug.getQuantity());
                    drug1.setQuantity(newQuant + "");
                    break;
                } else {
                    i++;
                }
            }
            if (i == stockList.size()){
                stockList.add(drug);
            }
        }
    }

    public void updateQuantity(Drug drug, String newQuantity){
        String key = drug.getExpirationDate();
        if (stock.containsKey(key)){
            List<Drug> list = stock.get(key);
            int index = findDrug(drug, list);
            if (index != -1){
                list.get(index).setQuantity(newQuantity);
                stock.put(key, list);
            }
        }
    }

    public void removeFromStock(List<Drug> drugs){
        for (Drug drug : drugs) {
            String key = drug.getExpirationDate();
            if (stock.containsKey(key)){
                List<Drug> list = stock.get(key);
                int index = findDrug(drug, list);
                if (index != -1){
                    list.remove(index);
                    stock.put(key, list);
                }
            }
        }
    }

    public int findDrug(Drug drug, List<Drug> list){
        int result = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(drug.getName())){
                result = i;
                break;
            }
        }
        return result;
    }

    public List<String> getFormatedStock(String currentDate){
        //TODO: Liste des médicaments et de la commande à afficher à la date courante.



        List<String> list = new ArrayList<>();
        list.add("STOCK " + currentDate);
        /*Collection<List<Drug>> drugsCollection = stock.values();
        List<Drug> drugs = new ArrayList<Drug>();
        for (List<Drug> list1 : drugsCollection) {
            drugs.addAll(list1);
        }*/
        for (Drug drug: stockList) {
            list.add(drug.parseString());
        }
        return list;
    }

    public TreeMap<String, List<Drug>> stockTest(){
        return stock;
    }

}

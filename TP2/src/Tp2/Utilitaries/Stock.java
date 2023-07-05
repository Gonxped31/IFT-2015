package Tp2.Utilitaries;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Stock {
    TreeMap<String, List<Drug>> stock = new TreeMap<>();

    public void add(List<Drug> drugs){
        for (Drug drug : drugs) {
            List<Drug> newDrugs = new ArrayList<>();
            String key = drug.getExpirationDate();
            if (stock.containsKey(key)) {
                List<Drug> list = stock.get(key);
                int index = findDrug(drug, list);
                if (index != -1){
                    list.get(index).setQuantity(drug.getQuantity());
                }
            } else {
                newDrugs.add(drug);
                stock.put(drug.getExpirationDate(), newDrugs);
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

    public List<List<String>> getFormatedStock(String currentDate){
        //TODO: Liste des médicaments et de la commande à afficher à la date courante.
        List<List<String>> list = new ArrayList<>();
        return list;
    }

}

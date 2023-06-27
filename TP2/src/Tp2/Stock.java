package Tp2;

import java.util.ArrayList;
import java.util.TreeMap;

public class Stock {

    private TreeMap<Integer, Drug> stock = new TreeMap<>();

    public void addDrugs(ArrayList<Drug> drugs) {
        ArrayList<Drug> durgsInStock = new ArrayList<>(stock.values());

        for (Drug drug : drugs) {
            if (durgsInStock.contains(drug)) {
                
            }
        }
    }
}

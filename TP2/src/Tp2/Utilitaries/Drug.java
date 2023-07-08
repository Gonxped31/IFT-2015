package Tp2.Utilitaries;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Drug {
    private String name;
    private String quantity;
    private String expirationDate;

    public Drug(String name, String quantity, String expirationDate){
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public Drug(){

    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Drug parseDrug(List<String> drug){
        return new Drug(drug.get(0), drug.get(1), drug.get(2));
    }

    public String parseString(){
        return this.name + " " + this.quantity + " " + this.expirationDate;
    }

}

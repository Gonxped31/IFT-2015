import java.util.List;

public class Medication {
    private String name;
    private String quantity;
    private String expirationDate;

    public Medication(String name, String quantity, String expirationDate){
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public Medication(){

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

    public Medication parseDrug(List<String> drug){
        return new Medication(drug.get(0), drug.get(1), drug.get(2));
    }

    public String parseString(){
        return this.name + " " + this.quantity + " " + this.expirationDate;
    }

}

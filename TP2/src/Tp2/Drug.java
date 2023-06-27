package Tp2;

public class Drug {

    private String name;
    private int quantity;
    private String expiration;


    public Drug(String name, int quantity, String expiration){
        this.name = name;
        this.quantity = quantity;
        this.expiration = expiration;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpiration() {
        return expiration;
    }
}

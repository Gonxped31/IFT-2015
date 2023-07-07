package Tp2.Utilitaries;

import java.util.List;

public class Prescription {
    private String name;
    private int dose;
    private int repetition;
    private String status;
    private int quantity;

    public Prescription(String name,int dose ,int repetition, String status){
        this.name = name;
        this.dose = dose;
        this.repetition = repetition;
        this.status = status;
        this.quantity = this.dose * this.repetition;
    }

    public Prescription(){

    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Prescription parsePrescription(List<String> prescription){
        return new Prescription(prescription.get(0), Integer.parseInt(prescription.get(1)), Integer.parseInt(prescription.get(2)), "OK");
    }

    public String parseString(){
        return this.name + "\t" + this.dose + "\t" + this.repetition + "\t" + this.status;
    }

    public String parseDateCommandStringFormat(){
        return this.name + "\t" + this.quantity;
    }

}

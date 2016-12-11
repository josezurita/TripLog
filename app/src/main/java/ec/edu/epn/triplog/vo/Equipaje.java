package ec.edu.epn.triplog.vo;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class Equipaje {
    private String item;
    private Boolean listo;

    public Equipaje() {
    }

    public Equipaje(String ítem, Boolean listo) {
        this.item = ítem;
        this.listo = listo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String ítem) {
        this.item = ítem;
    }

    public Boolean getListo() {
        return listo;
    }

    public void setListo(Boolean listo) {
        this.listo = listo;
    }


    public String toString() {
        return item;
    }

}
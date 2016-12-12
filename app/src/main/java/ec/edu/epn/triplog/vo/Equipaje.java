package ec.edu.epn.triplog.vo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */


@Table(name = "Equipaje")
public class Equipaje extends Model {

    public Equipaje() {
        super();
    }

    public Equipaje(String ítem, Boolean listo) {
        super();
        this.item = ítem;
        this.listo = listo;
    }

    @Column(name="item")
    private String item;

    @Column(name="listo")
    private Boolean listo;

    @Column(name="activo")
    private boolean activo;

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String toString() {
        return item;
    }

}
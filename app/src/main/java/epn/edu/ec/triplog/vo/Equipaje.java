package epn.edu.ec.triplog.vo;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */
public class Equipaje {

    public Equipaje() {
        super();
    }

    public Equipaje(String ítem, Boolean listo) {
        super();
        this.item = ítem;
        this.listo = listo;
    }

    private Integer idEquipaje;
    
    private String item;

    private Boolean listo;

    private boolean activo;

    private Viaje viaje;

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

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public Integer getIdEquipaje() {
        return idEquipaje;
    }

    public void setIdEquipaje(Integer idEquipaje) {
        this.idEquipaje = idEquipaje;
    }

}

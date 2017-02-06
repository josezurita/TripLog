package epn.edu.ec.triplog.vo;


import java.util.List;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class Viaje {

    public Viaje() {
        super();
    }
    
    public Viaje( String lugarViaje, Boolean favoritoViaje, String descripcionViaje, Usuario usuario) {
        super();
        this.descripcionViaje = descripcionViaje;
        this.lugarViaje = lugarViaje;
        this.favoritoViaje = favoritoViaje;
        this.usuario = usuario;
    }
    
    private Integer idViaje;

    private String nombre;

    private String descripcionViaje;

    private String lugarViaje;

    private boolean favoritoViaje;

    private Usuario usuario;

    private boolean activo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcionViaje() {
        return descripcionViaje;
    }

    public void setDescripcionViaje(String descripcionViaje) {
        this.descripcionViaje = descripcionViaje;
    }

    public String getLugarViaje() {
        return lugarViaje;
    }

    public void setLugarViaje(String lugarViaje) {
        this.lugarViaje = lugarViaje;
    }

    public boolean isFavoritoViaje() {
        return favoritoViaje;
    }

    public void setFavoritoViaje(boolean favoritoViaje) {
        this.favoritoViaje = favoritoViaje;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

}

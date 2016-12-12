package ec.edu.epn.triplog.vo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

@Table(name = "Viaje")
public class Viaje extends Model{

    public Viaje() {
        super();
    }
    
    public Viaje( String lugarViaje, Boolean favoritoViaje, String descripcionViaje) {
        super();
        this.descripcionViaje = descripcionViaje;
        this.lugarViaje = lugarViaje;
        this.favoritoViaje = favoritoViaje;
    }

    @Column(name = "nombre")
    private String nombre;

    @Column(name="descripcionviaje")
    private String descripcionViaje;

    @Column(name="lugarviaje")
    private String lugarViaje;

    @Column(name="favoritoviaje")
    private boolean favoritoViaje;

    @Column(name="usuario")
    private Usuario usuario;

    @Column(name="activo")
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

    public static List<Viaje> getAllByUseurId(Usuario usuario) {
        return new Select()
                .from(Viaje.class)
                .where("activo = ?",true)
                .and("usuario = ? ",usuario.getId())
                .execute();
    }
}

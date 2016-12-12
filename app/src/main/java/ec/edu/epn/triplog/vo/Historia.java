package ec.edu.epn.triplog.vo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Historia")
public class Historia extends Model {

    public Historia(){
        super();
    }

    public Historia(String nombre, String descripcion, String imagen) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }


    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name="activo")
    private boolean activo;

    @Column(name="viaje")
    private Viaje viaje;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public static Historia getBynombre(String nombre){
        return new Select().from(Historia.class).where("nombre = ?",nombre).executeSingle();
    }

    public static Historia getById(Long id){
        return new Select().from(Historia.class).where("Id = ?",id).executeSingle();
    }

    public static List<Historia> getAll(Viaje v) {
        return new Select()
                .from(Historia.class)
                .where("viaje = ?",v.getId())
                .and("activo = ?",true)
                .execute();
    }
    public static List<Historia> getAll() {
        return new Select()
                .from(Historia.class)
                .execute();
    }
}

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

    @Column(name = "nombre", unique = true)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;


    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getimagen() {
        return imagen;
    }

    public void setimagen(String imagen) {
        this.imagen = imagen;
    }


    public static Historia getBynombre(String nombre){
        return new Select().from(Historia.class).where("nombre = ?",nombre).executeSingle();
    }

    public static Historia getById(Long id){
        return new Select().from(Historia.class).where("Id = ?",id).executeSingle();
    }

    public static List<Historia> getAll() {
        return new Select()
                .from(Historia.class)
                .execute();
    }
}

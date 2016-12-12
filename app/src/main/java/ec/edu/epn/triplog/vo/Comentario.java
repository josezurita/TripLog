package ec.edu.epn.triplog.vo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Comentario")
public class Comentario extends Model {

    public Comentario(){
        super();
    }

    public Comentario(String contrasena, String descripcion, String nombre, String email) {
        super();
        this.descripcion = descripcion;
    }

    @Column(name = "descripcion", unique = true)
    private String descripcion;

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static Comentario getBydescripcion(String descripcion){
        return new Select().from(Comentario.class).where("descripcion = ?",descripcion).executeSingle();
    }

    public static Comentario getById(Long id){
        return new Select().from(Comentario.class).where("Id = ?",id).executeSingle();
    }

    public static List<Comentario> getAll() {
        return new Select()
                .from(Comentario.class)
                .execute();
    }
}

package ec.edu.epn.triplog.vo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Usuario")
public class Usuario extends Model {

    public Usuario(){
        super();
    }

    public Usuario(String contrasena, String usuario, String nombre, String email) {
        super();
        this.contrasena = contrasena;
        this.usuario = usuario;
        this.nombre = nombre;
        this.email = email;
    }

    @Column(name = "usuario", unique = true)
    private String usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name="activo")
    private boolean activo;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public static Usuario getByUsuario(String usuario){
        return new Select().from(Usuario.class).where("usuario = ?",usuario).and("activo = ?",true).executeSingle();
    }

    public static Usuario getById(Long id){
        return new Select().from(Usuario.class).where("Id = ?",id).and("activo = ?",true).executeSingle();
    }

    public static List<Usuario> getAll() {
        return new Select()
                .from(Usuario.class)
                .where("activo = ?",true)
                .execute();
    }
}

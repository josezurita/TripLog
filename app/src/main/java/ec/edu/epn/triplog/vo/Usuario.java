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

    @Column(name = "remote_id", unique = true)
    public long remoteId;

    @Column(name = "usuario", unique = true)
    public String usuario;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "email")
    public String email;

    @Column(name = "contrasena")
    public String contrasena;

    public static Usuario getByUsuario(String usuario){
        return new Select().from(Usuario.class).where("usuario = ?",usuario).executeSingle();
    }

    public static List<Usuario> getAll() {
        return new Select()
                .from(Usuario.class)
                .execute();
    }
}

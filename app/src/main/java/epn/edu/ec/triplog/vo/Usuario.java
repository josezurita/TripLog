package epn.edu.ec.triplog.vo;

import java.util.List;

public class Usuario {

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

    private Integer idUsuario;
    
    private String usuario;

    private String nombre;

    private String email;

    private String contrasena;

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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
}

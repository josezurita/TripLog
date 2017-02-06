package epn.edu.ec.triplog.vo;

import java.util.List;

public class Comentario {

    public Comentario(String contrasena, String descripcion, String nombre, String email) {
        this.descripcion = descripcion;
    }

    private String descripcion;

    private Usuario usuario;

    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

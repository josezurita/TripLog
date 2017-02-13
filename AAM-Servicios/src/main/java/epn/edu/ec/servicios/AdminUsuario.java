/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn.edu.ec.servicios;

import epn.edu.ec.triplog.vo.Usuario;
import epn.edu.ec.utilitarios.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author santi
 */
@Path("AdminUsuario")
public class AdminUsuario {

    @GET
    @Path("insertar")
    public String insertar(@QueryParam("usuario") String usuario,
            @QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("contrasena") String contrasena) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("insert into usuario (usuario,nombre,email,contrasena,activo) values (?,?,?,?,?)");
            ps.setString(1, usuario);
            ps.setString(2, nombre);
            ps.setString(3, email);
            ps.setString(4, contrasena);
            ps.setBoolean(5, true);
            ps.executeUpdate();
            ps.close();
            con.close();
            return "registro ingresado";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }
    
    @GET
    @Path("consultarPorUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> consultarPorUsuario(@QueryParam("usuario") String usuario) {
        List<Usuario> usuarios = new ArrayList<>();
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("select * from usuario where usuario = ? and activo=?");
            ps.setString(1, usuario);
            ps.setBoolean(2, true);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContrasena(rs.getString("contrasena"));
                u.setActivo(rs.getBoolean("activo"));
                //e.setViaje(new Viaje());
                
                usuarios.add(u);
            }

            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    @GET
    @Path("actualizar")
    public String actualizar(@QueryParam("usuario") String usuario,
            @QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("contrasena") String contrasena,
            @QueryParam("idUsuario") Integer idUsuario) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("update usuario set usuario=?,nombre=?,email=?,contrasena=? where idUsuario=?");
            ps.setString(1, usuario);
            ps.setString(2, nombre);
            ps.setString(3, email);
            ps.setString(4, contrasena);
            ps.setInt(5, idUsuario);
            ps.executeUpdate();
            ps.close();
            con.close();
            return "registro actualizado";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }
    
    @GET
    @Path("eliminarPorId")
    public String eliminarPorId(@QueryParam("idUsuario") Integer idUsuario) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("update usuario set activo=false where idUsuario=?");
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            ps.close();
            con.close();
            return "registro elimindo";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn.edu.ec.servicios;

import epn.edu.ec.triplog.vo.Equipaje;
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
        List<Usuario> equipajes = new ArrayList<>();
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

                equipajes.add(u);
            }

            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equipajes;
    }

    @GET
    @Path("consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equipaje> consultar() {
        List<Equipaje> equipajes = new ArrayList<>();
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("select * from equipaje");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Equipaje e = new Equipaje();
                e.setIdEquipaje(rs.getInt("idEquipaje"));
                e.setItem(rs.getString("item"));
                e.setListo(rs.getBoolean("listo"));
                e.setActivo(rs.getBoolean("activo"));
                //e.setViaje(new Viaje());

                equipajes.add(e);
            }

            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equipajes;
    }

    @GET
    @Path("actualizar")
    public String actualizar(@QueryParam("usuario") String usuario,
            @QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("contrasena") String contrasena) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("update equipaje set activo=false where idhistoria=?");
            ps.setString(1, usuario);
            ps.executeQuery();
            con.close();
            return "registro elimindo";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }
    
    @GET
    @Path("eliminarPorId")
    public String eliminarPorId(@QueryParam("idEquipaje") Integer idEquipaje) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("update equipaje set activo=false where idhistoria=?");
            ps.setInt(1, idEquipaje);
            ps.executeQuery();
            con.close();
            return "registro elimindo";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }

    @GET
    @Path("consultarPorId")
    public String consultarPorIds(@QueryParam("idEquipaje") Integer idEquipaje) {
        try {
            String resultado = "";

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + VariablesGlobales.IP + ":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("select * from equipaje where idhistoria=?");
            ps.setInt(1, idEquipaje);
            ResultSet rs = ps.executeQuery();

            List<String[]> equipajes = new ArrayList<>();
            while (rs.next()) {
                String[] u = new String[5];
                u[0] = String.valueOf(rs.getInt("idhistoria"));
                u[1] = String.valueOf(rs.getString("item"));
                u[2] = String.valueOf(rs.getBoolean("listo"));
                u[3] = String.valueOf(rs.getBoolean("activo"));
                u[4] = String.valueOf(rs.getInt("idviaje"));
                equipajes.add(u);
            }
            for (String[] str : equipajes) {
                resultado += "[";
                for (String s : str) {
                    resultado += s + ", ";
                }
                resultado += "]";
            }
            ps.close();
            con.close();
            return resultado;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }

}

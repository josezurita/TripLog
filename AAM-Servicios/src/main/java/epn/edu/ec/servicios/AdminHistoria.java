package epn.edu.ec.servicios;

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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import epn.edu.ec.triplog.vo.Historia;
import epn.edu.ec.utilitarios.VariablesGlobales;

@Path("AdminHistoria")
public class AdminHistoria {
	
	@GET
    @Path("insertar")
    public String insertar(@QueryParam("nombre") String nombre, @QueryParam("descripcion") String descripcion, @QueryParam("idViaje") int idViaje) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("insert into historia (nombre,descripcion,activo,idViaje) values (?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setBoolean(3, true);
            ps.setInt(4, idViaje);
            ps.executeUpdate();
            ps.close();
            con.close();
            return "registro ingresado";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminEquipaje.class.getName()).log(Level.SEVERE, null, ex);
              return "error";
        }
    }

    @GET
    @Path("consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Historia> consultar() {
        List<Historia> historias = new ArrayList<>();
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("select * from historia");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Historia historia = new Historia();
                historia.setIdHistoria(rs.getInt("idHistoria"));
                historia.setNombre(rs.getString("nombre"));
                historia.setDescripcion(rs.getString("descripcion"));
                historia.setImagen(rs.getString("imagen"));
                historia.setActivo(rs.getBoolean("activo"));
                AdminViaje admV = new AdminViaje();
                historia.setViaje(admV.consultarPorId(rs.getInt("idViaje")).get(0));
                //e.setViaje(new Viaje());
                historias.add(historia);
            }

            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminEquipaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historias;
    }

    @GET
    @Path("consultarHistoriaPorViaje")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Historia> consultarHistoriaPorViaje(@QueryParam("idViaje")int idViaje) {
        List<Historia> historias = new ArrayList<>();
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("select * from historia where idViaje=?");
            ps.setInt(1, idViaje);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Historia historia = new Historia();
                historia.setIdHistoria(rs.getInt("idHistoria"));
                historia.setNombre(rs.getString("nombre"));
                historia.setDescripcion(rs.getString("descripcion"));
                historia.setImagen(rs.getString("imagen"));
                historia.setActivo(rs.getBoolean("activo"));
                AdminViaje admV = new AdminViaje();
                historia.setViaje(admV.consultarPorId(rs.getInt("idViaje")).get(0));
                //e.setViaje(new Viaje());
                historias.add(historia);
            }

            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminEquipaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historias;
    }
    @GET
    @Path("eliminarPorId")
    public String eliminarPorId(@QueryParam("idHistoria") Integer idHistoria) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("update historia set activo=false where idhistoria=?");
            ps.setInt(1, idHistoria);
            ps.executeQuery();
            con.close();
            return "registro elimindo";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminEquipaje.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }
}

package epn.edu.ec.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import epn.edu.ec.utilitarios.VariablesGlobales;

@Path("AdminComentario")
public class AdminComentario {
	
	@GET
    @Path("prueba")
    public String prueba(@QueryParam("texto") String texto) {
        return texto;
    }

    @POST
    @Path("insertar")
    public String insertar(@QueryParam("item") String item, @QueryParam("idUsuario") int idUsuario) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
            PreparedStatement ps = con.prepareStatement("insert into comentario (descripcion,activo,idUsuario) values (?,?,?)");
            ps.setString(1, item);
            ps.setBoolean(2, true);
            ps.setInt(3, idUsuario);
            ps.executeUpdate();
            ps.close();
            con.close();
            return "registro ingresado";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminEquipaje.class.getName()).log(Level.SEVERE, null, ex);
              return "error";
        }
    }
}

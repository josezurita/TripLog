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
import epn.edu.ec.triplog.vo.Viaje;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import epn.edu.ec.triplog.vo.Equipaje;
import epn.edu.ec.utilitarios.VariablesGlobales;
@Path("AdminViaje")
public class AdminViaje {
	  @GET
	    @Path("prueba")
	    public String prueba(@QueryParam("texto") String texto) {
	        return texto;
	    }
	  
	  @GET
		@Path("insertar")
		public String insertar(
				
				@QueryParam("lugarViaje") String lugarViaje,
				@QueryParam("descripcionViaje") String descripcionViaje,
				@QueryParam("favoritoViaje") Boolean favoritoViaje,
				@QueryParam("activo") Boolean activo){
			try {
				Class.forName("org.postgresql.Driver");
				Connection con = (Connection) DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog",VariablesGlobales.USUARIO,VariablesGlobales.CLAVE);
				System.out.println("Conexión exitosa");
				
				PreparedStatement ps = (PreparedStatement) con.prepareStatement(
						"insert into viaje (lugar, descripcion, favorito,activo) values(?,?,?,?)");
				ps.setString(1, lugarViaje);
				ps.setString(2, descripcionViaje);
				ps.setBoolean(3, favoritoViaje);
				ps.setBoolean(4, activo);
				ps.executeUpdate();
				ps.close();
				con.close();

			return "viaje creado con éxito";

			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return "Error";
			}
			
		}
	  
	  @GET
	    @Path("consultar")
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<Viaje> consultar() {
	        List<Viaje> viajes = new ArrayList<>();
	        try {

	            Class.forName("org.postgresql.Driver");
	            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
	            PreparedStatement ps = con.prepareStatement("select * from viaje");
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Viaje v = new Viaje();
	                v.setIdViaje(rs.getInt("idviaje"));
	                v.setLugarViaje(rs.getString("lugar"));
	                v.setActivo(rs.getBoolean("activo"));
	                v.setActivo(rs.getBoolean("favorito"));
	                //e.setViaje(new Viaje());

	                viajes.add(v);
	            }

	            ps.close();
	            con.close();
	        } catch (ClassNotFoundException | SQLException ex) {
	            Logger.getLogger(AdminViaje.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return viajes;
	    }

	  
	  @GET
	    @Path("consultarPorId")
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<Viaje> consultarPorId(@QueryParam("idViaje") Integer idViaje) {
	        List<Viaje> viajes = new ArrayList<>();
	        try {

	            Class.forName("org.postgresql.Driver");
	            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
	            PreparedStatement ps = con.prepareStatement("select * from viaje where idViaje=?");
	            ps.setInt(1, idViaje);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Viaje v = new Viaje();
	                v.setIdViaje(rs.getInt("idviaje"));
	                v.setLugarViaje(rs.getString("lugar"));
	                v.setActivo(rs.getBoolean("activo"));
	                v.setActivo(rs.getBoolean("favorito"));
	                //e.setViaje(new Viaje());

	                viajes.add(v);
	            }

	            ps.close();
	            con.close();
	        } catch (ClassNotFoundException | SQLException ex) {
	            Logger.getLogger(AdminViaje.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return viajes;
	    }
	  
	  @GET
	    @Path("eliminarPorId")
	    public String eliminarPorId(@QueryParam("idViaje") Integer idViaje) {
	        try {

	            Class.forName("org.postgresql.Driver");
	            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
	            PreparedStatement ps = con.prepareStatement("update viaje set activo=false where idViaje=?");
	            ps.setInt(1, idViaje);
	            ps.executeUpdate();
	            con.close();
	            return "registro elimindo";
	        } catch (ClassNotFoundException | SQLException ex) {
	            Logger.getLogger(AdminViaje.class.getName()).log(Level.SEVERE, null, ex);
	            return "error";
	        }
	    }	  
}

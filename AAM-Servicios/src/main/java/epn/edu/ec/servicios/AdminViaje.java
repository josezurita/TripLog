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
import epn.edu.ec.triplog.vo.Usuario;
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
				
				@QueryParam("usuario") int usuario,
				@QueryParam("lugarViaje") String lugarViaje,
				@QueryParam("descripcionViaje") String descripcionViaje,
				@QueryParam("favoritoViaje") Boolean favoritoViaje,
				@QueryParam("activo") Boolean activo){
			try {
				
				
				Class.forName("org.postgresql.Driver");
				Connection con = (Connection) DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog",VariablesGlobales.USUARIO,VariablesGlobales.CLAVE);
				System.out.println("Conexión exitosa");
				
				PreparedStatement ps = (PreparedStatement) con.prepareStatement(
						"insert into viaje (idusuario, lugar, descripcion, favorito,activo) values(?,?,?,?,?)");
				ps.setInt(1, usuario);
				ps.setString(2, lugarViaje);
				ps.setString(3, descripcionViaje);
				ps.setBoolean(4, favoritoViaje);
				ps.setBoolean(5, activo);
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
	                v.setDescripcionViaje(rs.getString("descripcion"));
	                v.setActivo(rs.getBoolean("activo"));
	                v.setFavoritoViaje(rs.getBoolean("favorito"));
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
	                v.setDescripcionViaje(rs.getString("descripcion"));
	                v.setActivo(rs.getBoolean("activo"));
	                v.setFavoritoViaje(rs.getBoolean("favorito"));
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
	    @Path("consultarPorIdUsuario")
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<Viaje> consultarPorIdUsuario(@QueryParam("idUsuario") Integer idUsuario) {
	        List<Viaje> viajes = new ArrayList<>();
	        try {
	        	
	            Class.forName("org.postgresql.Driver");
	            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
	            PreparedStatement ps = con.prepareStatement("select * from viaje where idUsuario=? and activo=true");
	            ps.setInt(1, idUsuario);
	            ResultSet rs = ps.executeQuery();
	            
	            
	            while (rs.next()) {
	            	Usuario usr=new Usuario();
	            	usr.setIdUsuario(rs.getInt("idusuario"));
	                Viaje v = new Viaje();
	                v.setUsuario(usr);
	                v.setIdViaje(rs.getInt("idviaje"));
	                v.setLugarViaje(rs.getString("lugar"));
	                v.setDescripcionViaje(rs.getString("descripcion"));
	                v.setActivo(rs.getBoolean("activo"));
	                v.setFavoritoViaje(rs.getBoolean("favorito"));
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
	    @Path("consultarPorLugarViaje")
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<Viaje> consultarPorLugarViaje(@QueryParam("lugar") String lugar,
	    		@QueryParam("idUsuario") Integer idUsuario) {
	        List<Viaje> viajes = new ArrayList<>();
	        try {
	        	
	            Class.forName("org.postgresql.Driver");
	            Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
	            PreparedStatement ps = con.prepareStatement("select * from viaje where lugar=? and idUsuario=?" );
	            ps.setString(1, lugar);
	            ps.setInt(2, idUsuario);
	            ResultSet rs = ps.executeQuery();
	            
	            
	            while (rs.next()) {
	            	Usuario usr=new Usuario();
	            	usr.setIdUsuario(rs.getInt("idusuario"));
	                Viaje v = new Viaje();
	                v.setUsuario(usr);
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


@GET
@Path("modificar")
public String modificar(
		
		@QueryParam("usuario") int usuario,
		@QueryParam("lugarViaje") String lugarViaje,
		@QueryParam("descripcionViaje") String descripcionViaje,
		@QueryParam("idViaje") int idViaje){
	try {
				
		Class.forName("org.postgresql.Driver");
		Connection con = (Connection) DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog",VariablesGlobales.USUARIO,VariablesGlobales.CLAVE);
		System.out.println("Conexión exitosa");
		
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(
				"UPDATE viaje SET lugar =?, descripcion= ? WHERE idUsuario =?  and idViaje=?");
		
		ps.setString(1, lugarViaje);
		ps.setString(2, descripcionViaje);
		ps.setInt(3, usuario);
		ps.setInt(4, idViaje);
		
		ps.executeUpdate();
		ps.close();
		con.close();

	return "viaje modificado con éxito";

	} catch (Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		return "Error";
	}
	
}

@GET
@Path("favoritoPorId")
public String favoritoPorId(@QueryParam("idViaje") Integer idViaje,
		@QueryParam("favorito") Boolean favorito) {
    try {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://"+VariablesGlobales.IP+":5432/triplog", VariablesGlobales.USUARIO, VariablesGlobales.CLAVE);
        PreparedStatement ps = con.prepareStatement("update viaje set favorito=? where idViaje=?");
        ps.setBoolean(1, favorito);
        ps.setInt(2, idViaje);
        ps.executeUpdate();
        con.close();
        if(favorito==true){
        	return "Viaje agregado a favoritos";
        }else{
        	return "Viaje retirado de favoritos";
        }
        
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(AdminViaje.class.getName()).log(Level.SEVERE, null, ex);
        return "error";
    }
}
}
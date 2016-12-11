package ec.edu.epn.triplog.vo;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class Viaje {
    private String descripcion_viaje;
    private String lugar_viaje;
    private Boolean favorito_viaje;

    public Viaje( String lugar_viaje, Boolean favorito_viaje, String descripcion_viaje) {
        this.descripcion_viaje = descripcion_viaje;
        this.lugar_viaje = lugar_viaje;
        this.favorito_viaje = favorito_viaje;
    }

    public Viaje() {
    }



    public String getLugar_viaje() {
        return lugar_viaje;
    }

    public void setLugar_viaje(String lugar_viaje) {
        this.lugar_viaje = lugar_viaje;
    }

    public Boolean getFavorito_viaje() {
        return favorito_viaje;
    }

    public void setFavorito_viaje(Boolean favorito_viaje) {
        this.favorito_viaje = favorito_viaje;
    }

    public String getDescripcion_viaje() {
        return descripcion_viaje;
    }

    public void setDescripcion_viaje(String descripcion_viaje) {
        this.descripcion_viaje = descripcion_viaje;
    }


}

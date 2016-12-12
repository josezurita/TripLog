package ec.edu.epn.triplog.vo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */
@Table(name="Recordatorio")
public class Recordatorio extends Model{

    public Recordatorio() {
        super();
    }

    public Recordatorio(String strHora, String strDias, Boolean blSwitch) {
        super();
        this.strHora = strHora;
        this.strDias = strDias;
        this.blSwitch = blSwitch;
    }
    @Column(name = "strHora")
    private String strHora;

    @Column(name = "strDias")
    private String strDias;

    @Column(name = "blSwitch")
    private boolean blSwitch;

    @Column(name="activo")
    private boolean activo;

    public String getStrHora() {
        return strHora;
    }

    public void setStrHora(String strHora) {
        this.strHora = strHora;
    }

    public String getStrDias() {
        return strDias;
    }

    public void setStrDias(String strDias) {
        this.strDias = strDias;
    }

    public boolean isBlSwitch() {
        return blSwitch;
    }

    public void setBlSwitch(boolean blSwitch) {
        this.blSwitch = blSwitch;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

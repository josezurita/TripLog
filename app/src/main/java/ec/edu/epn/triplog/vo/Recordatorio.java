package ec.edu.epn.triplog.vo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */
@Table(name="Recordatorio")
public class Recordatorio extends Model{

    public Recordatorio() {
    }

    public Recordatorio(String strHora, String strDias, Boolean blSwitch) {
        this.strHora = strHora;
        this.strDias = strDias;
        this.blSwitch = blSwitch;
    }
    private String strHora;
    private String strDias;
    private Boolean blSwitch;
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
    public Boolean getBlSwitch() {
        return blSwitch;
    }
    public void setBlSwitch(Boolean blSwitch) {
        this.blSwitch = blSwitch;
    }
}

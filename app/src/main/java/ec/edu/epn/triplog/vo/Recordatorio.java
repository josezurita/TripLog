package ec.edu.epn.triplog.vo;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class Recordatorio {
    private String strHora;
    public Recordatorio(String strHora, String strDias, Boolean blSwitch) {
        this.strHora = strHora;
        this.strDias = strDias;
        this.blSwitch = blSwitch;
    }
    public Recordatorio() {
    }
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

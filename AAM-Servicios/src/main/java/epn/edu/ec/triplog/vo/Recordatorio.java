package epn.edu.ec.triplog.vo;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */
public class Recordatorio{

    public Recordatorio() {
        super();
    }

    public Recordatorio(String strHora, String strDias, Boolean blSwitch) {
        super();
        this.strHora = strHora;
        this.strDias = strDias;
        this.blSwitch = blSwitch;
    }
    
    private Integer idRecordatorio;
    
    private String strHora;

    private String strDias;

    private boolean blSwitch;

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

    public Integer getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(Integer idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }
}

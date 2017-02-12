package ec.edu.epn.triplog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import ec.edu.epn.triplog.vo.Usuario;
import ec.edu.epn.triplog.vo.Viaje;

import static ec.edu.epn.triplog.R.id.edtUsuario;

public class AdminViaje extends AppCompatActivity {

    private Usuario usuario;
    private EditText edtNombreViaje;
    private EditText edtDescripcion;
    private Spinner spnPaisViaje;
    private Viaje viaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_viaje);
        usuario= Usuario.getById(getIntent().getLongExtra("idUsuario",0));
        edtNombreViaje =(EditText)findViewById(R.id.edtNombreViaje);
        edtDescripcion =(EditText)findViewById(R.id.edtDescripcionViaje);
        viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));
        if(viaje!=null){
            ((EditText) findViewById(R.id.edtNombreViaje)).setText(viaje.getLugarViaje());
            ((EditText) findViewById(R.id.edtDescripcionViaje)).setText(viaje.getDescripcionViaje());
        }

    }

    public void guardarViaje(View v){
        if(edtNombreViaje.getText().toString().isEmpty()||edtDescripcion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }
      //  new AdminViajeAsyn.execute(edtUsuario.getText().toString());
        /*
        if(viaje==null){
            viaje=new Viaje();
            viaje.setUsuario(usuario);
        }
        viaje.setNombre(edtNombreViaje.getText().toString());
        viaje.setDescripcionViaje(edtDescripcion.getText().toString());
        viaje.setLugarViaje(edtNombreViaje.getText().toString());
        viaje.setFavoritoViaje(false);
        viaje.setActivo(true);

        viaje.save();
        Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
        finish();
        */
    }

    public class ViajeRegisterAsync extends AsyncTask<epn.edu.ec.triplog.vo.Viaje,Void,String> {
        @Override
        protected String doInBackground(epn.edu.ec.triplog.vo.Viaje... viajes) {

            epn.edu.ec.triplog.vo.Viaje viaje=viajes[0];
            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "insertar?lugarViaje={var1}&descripcionViaje={var2}&favoritoViaje={var3}&activo={var4}&usuario={var5}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",viaje.getLugarViaje());
            valores.put("var2",viaje.getDescripcionViaje());
            valores.put("var3",viaje.isFavoritoViaje());
            valores.put("var4",viaje.isActivo());
            valores.put("var5",viaje.getUsuario());
            return restTemplate.getForObject(url,String.class,valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}


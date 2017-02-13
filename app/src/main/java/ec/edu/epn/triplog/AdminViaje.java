package ec.edu.epn.triplog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;

import ec.edu.epn.triplog.vo.Usuario;
import ec.edu.epn.triplog.vo.Viaje;


public class AdminViaje extends AppCompatActivity {


    private Usuario usuario;
    private EditText edtNombreViaje;
    private EditText edtDescripcion;
    private Spinner spnPaisViaje;
    //private epn.edu.ec.triplog.vo.
    private Viaje viaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_viaje);
        usuario = Usuario.getById(getIntent().getLongExtra("idUsuario", 0));
        edtNombreViaje = (EditText) findViewById(R.id.edtNombreViaje);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcionViaje);
        viaje = Viaje.getById(getIntent().getLongExtra("idViaje", 0));
        if (viaje != null) {
            ((EditText) findViewById(R.id.edtNombreViaje)).setText(viaje.getLugarViaje());
            ((EditText) findViewById(R.id.edtDescripcionViaje)).setText(viaje.getDescripcionViaje());
        }

    }

    public void guardarViaje(View v) {
        if (edtNombreViaje.getText().toString().isEmpty() || edtDescripcion.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
        new ViajeRegisterAsync().execute();
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

    public class ViajeVerifyAsync extends AsyncTask<String, Void, List<Viaje>> {
        @Override
        protected List<Viaje> doInBackground(String... strings) {

            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "consultarPorLugarViaje?lugar={var1}&idUsuario={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", strings[0]);
            valores.put("var2", strings[1]);
            ResponseEntity<Viaje[]> responseEntity = restTemplate.getForEntity(url, Viaje[].class, valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<Viaje> s) {

            if (!s.isEmpty()) {
                Toast.makeText(getApplicationContext(), "viaje ya existe " + s.get(0).getLugarViaje(), Toast.LENGTH_SHORT).show();
                return;
            }
            Viaje viaj;

            if (viaje == null) {
                viaj = new Viaje();
                viaj.setUsuario(usuario);
                viaj.setLugarViaje(edtNombreViaje.getText().toString().trim());
                viaj.setDescripcionViaje(edtDescripcion.getText().toString().trim());
                viaj.setFavoritoViaje(false);
                viaj.setActivo(true);
                new AdminViaje.ViajeRegisterAsync().execute(viaj);
            } else {
                viaj = viaje;
            }
        }
    }

    public class ViajeRegisterAsync extends AsyncTask<Viaje, Void, String> {
        @Override
        protected String doInBackground(Viaje... viajes) {

            Viaje viaje = viajes[0];
            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "insertar?lugarViaje={var1}&descripcionViaje={var2}&favoritoViaje={var3}&activo={var4}&usuario={var5}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", viaje.getLugarViaje());
            valores.put("var2", viaje.getDescripcionViaje());
            valores.put("var3", viaje.isFavoritoViaje());
            valores.put("var4", viaje.isActivo());
            valores.put("var5", viaje.getUsuario());
            return restTemplate.getForObject(url, String.class, valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}


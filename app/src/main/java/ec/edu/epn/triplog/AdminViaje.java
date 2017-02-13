package ec.edu.epn.triplog;

import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;

import epn.edu.ec.triplog.vo.Usuario;
import epn.edu.ec.triplog.vo.Viaje;


public class AdminViaje extends AppCompatActivity {


    private EditText edtNombreViaje;
    private EditText edtDescripcion;
    private Spinner spnPaisViaje;
    private Viaje viaje;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_viaje);
        usuario = new Usuario();
        viaje=new Viaje();
        //String strViaje=getIntent().getStringExtra("usuario");
        usuario.setIdUsuario(getIntent().getIntExtra("idUsuario", 0));
        viaje.setUsuario(usuario);
        viaje.setIdViaje(getIntent().getIntExtra("idViaje", 0));
        viaje.setLugarViaje(getIntent().getStringExtra("lugarViaje"));
        viaje.setDescripcionViaje(getIntent().getStringExtra("descripcionViaje"));
//        Toast.makeText(getApplicationContext(), idusuario.toString()+" "+ getIntent().getStringExtra("idViaje").toString() , Toast.LENGTH_SHORT).show();

        edtNombreViaje = (EditText) findViewById(R.id.edtNombreViaje);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcionViaje);


        //usuario.setIdUsuario(getIntent().getExtras().getInt("idUsuario"));

        if (viaje.getIdViaje() != 0) {
            ((EditText) findViewById(R.id.edtNombreViaje)).setText(viaje.getLugarViaje());
            ((EditText) findViewById(R.id.edtDescripcionViaje)).setText(viaje.getDescripcionViaje());

        }


    }

    public void guardarViaje(View v) {

        if (edtNombreViaje.getText().toString().isEmpty() || edtDescripcion.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (viaje.getIdViaje() != 0) {
            viaje.setLugarViaje(edtNombreViaje.getText().toString());
            viaje.setDescripcionViaje(edtDescripcion.getText().toString());
            new ViajeModificarAsync().execute(viaje);
            return;
        }
        new ViajeVerifyAsync().execute(edtNombreViaje.getText().toString(), usuario.getIdUsuario().toString());
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
                    "consultarPorLugarViaje?lugar={var1}&idUsuario={var2}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", strings[0]);
            valores.put("var2", Integer.parseInt(strings[1]));
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
            valores.put("var4", true);
            valores.put("var5", usuario.getIdUsuario());
            return restTemplate.getForObject(url, String.class, valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public class ViajeModificarAsync extends AsyncTask<Viaje, Void, String> {
        @Override
        protected String doInBackground(Viaje... viajes) {

            Viaje viaje = viajes[0];
            //String str="UPDATE viaje SET lugar ='holahola', descripcion= 'mrhmrh'WHERE idUsuario = 1 and idViaje=23";
            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "modificar?lugarViaje={var1}&descripcionViaje={var2}&usuario={var3}&idViaje={var4}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", viaje.getLugarViaje());
            valores.put("var2", viaje.getDescripcionViaje());
            valores.put("var3", viaje.getUsuario().getIdUsuario());
            valores.put("var4", viaje.getIdViaje());

            return restTemplate.getForObject(url, String.class, valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro modificado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}


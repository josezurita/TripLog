package ec.edu.epn.triplog;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import ec.edu.epn.triplog.vo.Historia;
import ec.edu.epn.triplog.vo.Viaje;

public class RegHistorias extends AppCompatActivity {

    private Viaje viaje;
    private EditText etNombreH;
    private EditText etDescH;
    private Historia historia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_historias);
        viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));
        etNombreH=(EditText)findViewById(R.id.et_nombreH);
        etDescH=(EditText)findViewById(R.id.et_descH);
        historia=Historia.getById(getIntent().getLongExtra("idHistoria",0));
        if(historia!=null){
            etNombreH.setText(historia.getNombre());
            etDescH.setText(historia.getDescripcion());
        }
    }

     public void guardarHistoria(View v){
         if(etNombreH.getText().toString().isEmpty()||etDescH.getText().toString().isEmpty()){
             Toast.makeText(getApplicationContext(), "Ingrese los datos", Toast.LENGTH_SHORT).show();
             return;
         }
         if(historia==null){
             historia = new Historia();
             historia.setViaje(viaje);
             historia.setActivo(true);
             historia.setNombre(etNombreH.getText().toString().trim());
             historia.setDescripcion(etDescH.getText().toString().trim());
             new HistoriaCrearAsync().execute(historia);
         }else{
             historia.setActivo(true);
             historia.setNombre(etNombreH.getText().toString().trim());
             historia.setDescripcion(etDescH.getText().toString().trim());
             new HistoriaEditarAsync().execute(historia);
         }

         //historia.save();
         etNombreH.setText("");
         etDescH.setText("");
         Toast.makeText(getApplicationContext(), "Equipaje ingresado", Toast.LENGTH_SHORT).show();
         finish();
     }

    public class HistoriaCrearAsync extends AsyncTask<Historia,Void,String> {
        @Override
        protected String doInBackground(Historia... historias) {
            Historia historia = historias[0];
            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminHistoria/" +
                    "insertar?nombre={var1}&descripcion={var2}&idViaje={var3}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1",historia.getNombre());
            valores.put("var2",historia.getDescripcion());
            valores.put("var3",historia.getViaje().getId());
            return restTemplate.getForObject(url, String.class, valores);
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro creado", Toast.LENGTH_SHORT).show();
        }
    }

    public class HistoriaEditarAsync extends AsyncTask<Historia,Void,String> {
        @Override
        protected String doInBackground(Historia... historias) {
            Historia historia = historias[0];
            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminHistoria/" +
                    "modificar?idHistoria={var1}&nombre={var2}&descripcion={var3}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1",historia.getId());
            valores.put("var2",historia.getNombre());
            valores.put("var3",historia.getDescripcion());
            return restTemplate.getForObject(url, String.class, valores);
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro modificado", Toast.LENGTH_SHORT).show();
        }
    }
}

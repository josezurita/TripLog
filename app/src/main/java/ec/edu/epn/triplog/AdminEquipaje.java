package ec.edu.epn.triplog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.edu.epn.triplog.Adaptadores.AdaptadorEquipaje;
import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import epn.edu.ec.triplog.vo.Equipaje;
import epn.edu.ec.triplog.vo.Viaje;

public class    AdminEquipaje extends AppCompatActivity {
    private ListView lv_equipaje;
    private TextView tv_equipaje;
    int idViaje;

    Equipaje datos[] = new Equipaje[3];
    private Viaje viaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_equipaje);
        //viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));
        idViaje=getIntent().getIntExtra("idViaje",0);
        new EquipajeViajeAsync().execute(idViaje);
        lv_equipaje = (ListView) findViewById(R.id.lv_equipaje);
        lv_equipaje.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tv_equipaje=(TextView)findViewById(R.id.et_nombreEquipaje);
        //List<Equipaje> lstEquipajes=Equipaje.getAllByViajeId(viaje);
        //AdaptadorEquipaje ua = new AdaptadorEquipaje(this,lstEquipajes.toArray(new Equipaje[lstEquipajes.size()]));
        //lv_equipaje.setAdapter(ua);
    }
    public void onListItemClick(ListView parent, View v, int position, long id) {
        CheckedTextView item = (CheckedTextView) v;
        item.setEnabled(datos[position].getListo());
    }

    public void guardarEquipaje(View view){

        if(tv_equipaje.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        Equipaje equip = new Equipaje();
        equip.setActivo(true);
        equip.setItem(tv_equipaje.getText().toString().trim());
        equip.setListo(false);
        equip.setViaje(viaje);
        //equip.save();
        new EquipajeAsync().execute(new Equipaje[]{equip});
        tv_equipaje.setText("");
        Toast.makeText(getApplicationContext(), "Equipaje ingresado", Toast.LENGTH_SHORT).show();
        //List<Equipaje> lstEquipajes=Equipaje.getAllByViajeId(viaje);
        //AdaptadorEquipaje ua = new AdaptadorEquipaje(this,lstEquipajes.toArray(new Equipaje[lstEquipajes.size()]));
        //lv_equipaje.setAdapter(ua);
    }

    public class EquipajeAsync extends AsyncTask<Equipaje,Void,String> {
        @Override
        protected String doInBackground(Equipaje... equipajes) {
            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminEquipaje/insertar?item={var1}" +
                    "&idViaje={var2}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",equipajes[0].getItem());
            valores.put("var2",idViaje);
            return restTemplate.getForObject(url,String.class,valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            new EquipajeViajeAsync().execute(idViaje);
        }
    }

    public void setAdaptadorHistoria(List<Equipaje> lstEquipajes){

        AdaptadorEquipaje ua = new AdaptadorEquipaje(this,lstEquipajes.toArray(new Equipaje[lstEquipajes.size()]));
        lv_equipaje.setAdapter(ua);
    }

    public class EquipajeViajeAsync extends AsyncTask<Integer,Void,List<Equipaje>> {

        @Override
        protected List<epn.edu.ec.triplog.vo.Equipaje> doInBackground(Integer... args) {

            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/" +
                    "AdminEquipaje/consultarPorIdViaje?idViaje={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", args[0]);
            ResponseEntity<Equipaje[]> responseEntity = restTemplate.getForEntity(url, epn.edu.ec.triplog.vo.Equipaje[].class, valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<epn.edu.ec.triplog.vo.Equipaje> s) {
            setAdaptadorHistoria(s);
        }
    }

    public class EditarEquipajeAsync extends AsyncTask<Equipaje,Void,String> {
        @Override
        protected String doInBackground(Equipaje... equipajes) {
            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminEquipaje/modificar?idEquipaje={var1}" +
                    "&item={var2}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",equipajes[0].getIdEquipaje());
            valores.put("var2",equipajes[0].getItem());
            return restTemplate.getForObject(url,String.class,valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            new EquipajeViajeAsync().execute(idViaje);
        }
    }

    public class ListoEquipajeAsync extends AsyncTask<Equipaje,Void,String> {
        @Override
        protected String doInBackground(Equipaje... equipajes) {
            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminEquipaje/insertar?item={var1}" +
                    "&idViaje={var2}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",equipajes[0].getItem());
            valores.put("var2",idViaje);
            return restTemplate.getForObject(url,String.class,valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            new EquipajeViajeAsync().execute(idViaje);
        }
    }
}


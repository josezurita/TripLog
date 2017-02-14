package ec.edu.epn.triplog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.edu.epn.triplog.Adaptadores.AdaptadorHistoria;
import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import epn.edu.ec.triplog.vo.Historia;
import epn.edu.ec.triplog.vo.Viaje;

public class AdminHistorias extends AppCompatActivity  {

    private Viaje viaje;
    private ListView lv_historias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historias);

        //viaje= Viaje.getById();
        lv_historias = (ListView) findViewById(R.id.lv_historias);
        lv_historias.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        new ViajeInformationAsync().execute(getIntent().getIntExtra("idViaje",0)+"");

        //List<Historia> lstHistorias=Historia.getAll(viaje);
        //System.out.println(lstHistorias.size());
        //AdaptadorHistoria av = new AdaptadorHistoria(this,lstHistorias.toArray(new Historia[lstHistorias.size()]),this);
        //lv_historias.setAdapter(av);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(viaje!=null) {
            new HistoriaViajeAsync().execute(viaje.getIdViaje());
        }
        //List<Historia> lstHistorias=Historia.getAll(viaje);
        //AdaptadorHistoria av = new AdaptadorHistoria(this,lstHistorias.toArray(new Historia[lstHistorias.size()]),this);
        //lv_historias.setAdapter(av);
    }
    public void abrirRegistroHistoria(View v){
        Intent intent=new Intent(getApplicationContext(),RegHistorias.class);
        intent.putExtra("idViaje",viaje.getIdViaje());
        startActivity(intent);
    }
    public void actualizarHistorias(){
        if(viaje!=null) {
            new HistoriaViajeAsync().execute(viaje.getIdViaje());
        }
        //List<Historia> lstHistorias=Historia.getAll(viaje);
        //AdaptadorHistoria av = new AdaptadorHistoria(this,lstHistorias.toArray(new Historia[lstHistorias.size()]),this);
        //lv_historias.setAdapter(av);
    }

    /*public static Historia getBynombre(String nombre){
        return new Select().from(Historia.class).where("nombre = ?",nombre).executeSingle();
    }

    public static Historia getById(Long id){
        return new Select().from(Historia.class).where("Id = ?",id).executeSingle();
    }

    public static List<Historia> getAll(Viaje v) {
        return new Select()
                .from(Historia.class)
                .where("viaje = ?",v.getId())
                .and("activo = ?",true)
                .execute();
    }
    public static List<Historia> getAll() {
        return new Select()
                .from(Historia.class)
                .execute();
    }*/
    public class ViajeInformationAsync extends AsyncTask<String,Void,List<epn.edu.ec.triplog.vo.Viaje>> {
        @Override
        protected List<epn.edu.ec.triplog.vo.Viaje> doInBackground(String... strings) {

            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "consultarPorId?idViaje={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", Integer.valueOf(strings[0]));
            ResponseEntity<epn.edu.ec.triplog.vo.Viaje[]> responseEntity = restTemplate.getForEntity(url, epn.edu.ec.triplog.vo.Viaje[].class, valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<epn.edu.ec.triplog.vo.Viaje> s) {
            viaje = s.get(0);
            setControlerConf();
            new AdminHistorias.HistoriaViajeAsync().execute(viaje.getIdViaje());
        }
    }

    public class HistoriaViajeAsync extends AsyncTask<Integer,Void,List<epn.edu.ec.triplog.vo.Historia>> {

        @Override
        protected List<epn.edu.ec.triplog.vo.Historia> doInBackground(Integer... args) {

            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminHistoria/" +
                    "consultarHistoriaPorViaje?idViaje={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", args[0]);
            ResponseEntity<epn.edu.ec.triplog.vo.Historia[]> responseEntity = restTemplate.getForEntity(url, epn.edu.ec.triplog.vo.Historia[].class, valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<Historia> s) {
            setAdaptadorHistoria(s);
        }
    }

    public void setAdaptadorHistoria(List<Historia> historias){
        AdaptadorHistoria ah = new AdaptadorHistoria(this, historias.toArray(new epn.edu.ec.triplog.vo.Historia[historias.size()]),this);
        lv_historias.setAdapter(ah);
    }


    public void setControlerConf(){
        lv_historias = (ListView) findViewById(R.id.lv_historias);
        lv_historias.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ImageButton imgButton = (ImageButton) findViewById(R.id.imgBtnAddHistoria);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegHistorias.class);
                intent.putExtra("idViaje",viaje.getIdViaje());
                startActivity(intent);
            }
        });
    }

}

package ec.edu.epn.triplog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ec.edu.epn.triplog.Adaptadores.AdaptadorHistoria;
import ec.edu.epn.triplog.Adaptadores.AdaptadorViaje;
import ec.edu.epn.triplog.vo.Historia;
import ec.edu.epn.triplog.vo.Viaje;

import static ec.edu.epn.triplog.R.id.lv_historias;

public class AdminHistorias extends AppCompatActivity  {

    private Viaje viaje;
    private ListView lv_historias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historias);

        viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));

        lv_historias = (ListView) findViewById(R.id.lv_historias);
        lv_historias.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        List<Historia> lstHistorias=Historia.getAll(viaje);

        AdaptadorHistoria av = new AdaptadorHistoria(this,lstHistorias.toArray(new Historia[lstHistorias.size()]));
        lv_historias.setAdapter(av);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Historia> lstHistorias=Historia.getAll(viaje);

        AdaptadorHistoria av = new AdaptadorHistoria(this,lstHistorias.toArray(new Historia[lstHistorias.size()]));
        lv_historias.setAdapter(av);
    }


    public void abrirRegistroHistoria(View v){
        Intent intent=new Intent(getApplicationContext(),RegHistorias.class);
        intent.putExtra("idViaje",viaje.getId());
        startActivity(intent);
    }

}

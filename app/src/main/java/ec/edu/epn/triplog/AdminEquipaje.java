package ec.edu.epn.triplog;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ec.edu.epn.triplog.Adaptadores.AdaptadorEquipaje;
import ec.edu.epn.triplog.vo.Equipaje;
import ec.edu.epn.triplog.vo.Usuario;
import ec.edu.epn.triplog.vo.Viaje;

public class    AdminEquipaje extends AppCompatActivity {
    private ListView lv_equipaje;
    private TextView tv_equipaje;
    Equipaje datos[] = new Equipaje[3];
    private Viaje viaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_equipaje);
        viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));
        lv_equipaje = (ListView) findViewById(R.id.lv_equipaje);
        lv_equipaje.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tv_equipaje=(TextView)findViewById(R.id.et_nombreEquipaje);
        List<Equipaje> lstEquipajes=Equipaje.getAllByViajeId(viaje);
        AdaptadorEquipaje ua = new AdaptadorEquipaje(this,lstEquipajes.toArray(new Equipaje[lstEquipajes.size()]));
        lv_equipaje.setAdapter(ua);
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
        equip.save();
        tv_equipaje.setText("");
        Toast.makeText(getApplicationContext(), "Equipaje ingresado", Toast.LENGTH_SHORT).show();
        List<Equipaje> lstEquipajes=Equipaje.getAllByViajeId(viaje);
        AdaptadorEquipaje ua = new AdaptadorEquipaje(this,lstEquipajes.toArray(new Equipaje[lstEquipajes.size()]));
        lv_equipaje.setAdapter(ua);
    }
}


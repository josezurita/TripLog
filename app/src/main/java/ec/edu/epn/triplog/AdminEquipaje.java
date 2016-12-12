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

import java.util.List;

import ec.edu.epn.triplog.Adaptadores.AdaptadorEquipaje;
import ec.edu.epn.triplog.vo.Equipaje;
import ec.edu.epn.triplog.vo.Usuario;
import ec.edu.epn.triplog.vo.Viaje;

public class    AdminEquipaje extends AppCompatActivity {
    private ListView lv_equipaje;
    Equipaje datos[] = new Equipaje[3];
    private Viaje viaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_equipaje);
        viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));
        lv_equipaje = (ListView) findViewById(R.id.lv_equipaje);
        lv_equipaje.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        List<Equipaje> lstEquipajes=Equipaje.getAllEquipaje();
        AdaptadorEquipaje ua = new AdaptadorEquipaje(this,lstEquipajes.toArray(new Equipaje[lstEquipajes.size()]));
        lv_equipaje.setAdapter(ua);
    }
    public void onListItemClick(ListView parent, View v, int position, long id) {
        CheckedTextView item = (CheckedTextView) v;
        item.setEnabled(datos[position].getListo());
    }
}


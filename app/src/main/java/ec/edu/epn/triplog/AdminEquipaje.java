package ec.edu.epn.triplog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.PopupMenu;

import ec.edu.epn.triplog.Adaptadores.AdaptadorEquipaje;
import ec.edu.epn.triplog.vo.Equipaje;

public class    AdminEquipaje extends AppCompatActivity {
    private ListView lv_equipaje;
    Equipaje datos[] = new Equipaje[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_equipaje);
        lv_equipaje = (ListView) findViewById(R.id.lv_equipaje);
        lv_equipaje.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Equipaje datos[] = new Equipaje[3];
        datos[0] = new Equipaje("Cámara", false);
        datos[1] = new Equipaje("Zapatos", true);
        datos[2] = new Equipaje("Efectivo", false);

        AdaptadorEquipaje ua = new AdaptadorEquipaje(this,datos);
        lv_equipaje.setAdapter(ua);
    }
    public void onListItemClick(ListView parent, View v, int position, long id) {
        CheckedTextView item = (CheckedTextView) v;
        item.setEnabled(datos[position].getListo());
    }
    public void showPopUp(View v){
        PopupMenu popup = new PopupMenu(this,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_lv_equipaje,popup.getMenu());
        popup.show();
    }
}


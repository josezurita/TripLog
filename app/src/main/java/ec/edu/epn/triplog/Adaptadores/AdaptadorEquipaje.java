package ec.edu.epn.triplog.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import ec.edu.epn.triplog.AdminEquipaje;
import ec.edu.epn.triplog.AdminViaje;
import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.vo.Equipaje;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorEquipaje extends ArrayAdapter implements PopupMenu.OnMenuItemClickListener{

    private Equipaje[] equipaje;
    private Equipaje equipajeActual;
    private TextView tv_equipaje;


    public AdaptadorEquipaje(Context context, Equipaje[] equipaje) {
        super(context, android.R.layout.simple_expandable_list_item_1, equipaje);
        this.equipaje = equipaje;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //converView representa toda la fila del item
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.lv_equipaje, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tv_equipaje);

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.cb_equipaje);


        String nombreEquipaje = equipaje[position].getItem();
        tv.setText(equipaje[position].getItem());
        cb.setChecked(equipaje[position].getListo());
        equipaje[position].setListo(cb.isChecked());


        final ImageView ivMenu=(ImageView)convertView.findViewById(R.id.iv_popup_equipaje);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipajeActual=equipaje[position];
                PopupMenu popup = new PopupMenu(getContext(),view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_lv_equipaje,popup.getMenu());
                popup.show();
            }
        });
        return convertView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_editar_equipaje:
                Toast.makeText(getContext(), "Editar equipaje", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_eliminar_equipaje:
                Toast.makeText(getContext(), "Eliminar equipaje", Toast.LENGTH_SHORT).show();
                return true;
            /*case R.id.action_eliminar_viaje:
                vi.setActivo(false);
                vi.save();
                Toast.makeText(getContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
                this.notifyDataSetChanged();
                homeActivity.actualizar();
                return true;*/
            default:
                return false;
        }
    }


}

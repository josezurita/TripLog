package ec.edu.epn.triplog.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.vo.Equipaje;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorEquipaje extends ArrayAdapter {

    private Equipaje[] equipaje;

    public AdaptadorEquipaje(Context context, Equipaje[] equipaje) {
        super(context, android.R.layout.simple_expandable_list_item_1, equipaje);
        this.equipaje = equipaje;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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


        return convertView;

    }



}

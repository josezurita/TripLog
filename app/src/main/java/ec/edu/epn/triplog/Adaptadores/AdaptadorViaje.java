package ec.edu.epn.triplog.Adaptadores;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.vo.Equipaje;
import ec.edu.epn.triplog.vo.Viaje;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorViaje extends ArrayAdapter {

    private Viaje[] viaje;

    public AdaptadorViaje(Context context, Viaje[] viaje) {
        super(context, android.R.layout.simple_expandable_list_item_1, viaje);
        this.viaje = viaje;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //converView representa toda la fila del item
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.lv_equipaje, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tv_lugarViaje);
        //Image img= (Image)convertView.findViewById(R.id.img_viaje);
        TextView tv1 = (TextView) convertView.findViewById(R.id.tv_descViaje);


        String lugarViaje = viaje[position].getLugar_viaje();
        tv.setText(viaje[position].getLugar_viaje());

        String descViaje = viaje[position].getDescripcion_viaje();
        tv1.setText(viaje[position].getDescripcion_viaje());

        return convertView;

    }
}
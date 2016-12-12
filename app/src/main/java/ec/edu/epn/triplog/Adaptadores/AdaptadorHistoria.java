package ec.edu.epn.triplog.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.vo.Historia;
import ec.edu.epn.triplog.vo.Viaje;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorHistoria extends ArrayAdapter {

    private Historia[] historia;

    public AdaptadorHistoria(Context context, Historia[] historia) {
        super(context, android.R.layout.simple_expandable_list_item_1, historia);
        this.historia = historia;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //converView representa toda la fila del item
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.lv_historias, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tv_nombreHis);
        //Image img= (Image)convertView.findViewById(R.id.img_viaje);
        TextView tv1 = (TextView) convertView.findViewById(R.id.tv_descHis);

        tv.setText(historia[position].getNombre());

        tv1.setText(historia[position].getDescripcion());


        return convertView;

    }

}
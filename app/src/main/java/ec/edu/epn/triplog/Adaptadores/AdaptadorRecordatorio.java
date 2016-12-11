package ec.edu.epn.triplog.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import ec.edu.epn.triplog.AdminRecordatorio;
import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.vo.Recordatorio;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorRecordatorio extends ArrayAdapter {
    private Recordatorio[] alarma;
    public AdaptadorRecordatorio(Context context, Recordatorio[] recordatorio) {
        super(context,android.R.layout.simple_expandable_list_item_1,recordatorio);
        this.alarma=alarma;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //converView representa toda la fila del item
        if(convertView==null){
            LayoutInflater li= LayoutInflater.from(getContext());
            convertView=li.inflate(R.layout.lv_horas,null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tv_Hora);
        TextView tv1=(TextView)convertView.findViewById(R.id.tv_Dias);
        Switch sw=(Switch)convertView.findViewById(R.id.sw_alarma);
        tv.setText(alarma[position].getStrHora());
        tv1.setText(alarma[position].getStrDias());
        sw.setChecked(alarma[position].getBlSwitch());
        return convertView;
    }

}

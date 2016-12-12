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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import ec.edu.epn.triplog.AdminHistorias;
import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.RegHistorias;
import ec.edu.epn.triplog.vo.Historia;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorHistoria extends ArrayAdapter implements PopupMenu.OnMenuItemClickListener {

    private Historia[] historia;
    private Historia hist;
    AdminHistorias adminHistorias;

    public AdaptadorHistoria(Context context, Historia[] historia, AdminHistorias adminHistorias) {
        super(context, android.R.layout.simple_expandable_list_item_1, historia);
        this.historia = historia;
        this.adminHistorias=adminHistorias;
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

        final ImageView ivMenu=(ImageView)convertView.findViewById(R.id.iv_more_equip);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hist=historia[position];
                PopupMenu popup = new PopupMenu(getContext(),view);
                popup.setOnMenuItemClickListener(AdaptadorHistoria.this);
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
                Intent intent1= new Intent(getContext(),RegHistorias.class);
                intent1.putExtra("idHistoria", hist.getId());
                getContext().startActivity(intent1);
                Toast.makeText(getContext(),"Editar",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_eliminar_equipaje:
                hist.setActivo(false);
                hist.save();
                Toast.makeText(getContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
                this.notifyDataSetChanged();
                adminHistorias.actualizarHistorias();
                return true;
            default:
                return false;
        }
    }
}
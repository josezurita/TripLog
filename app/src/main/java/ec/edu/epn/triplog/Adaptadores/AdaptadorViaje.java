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

import ec.edu.epn.triplog.AdminEquipaje;
import ec.edu.epn.triplog.AdminHistorias;
import ec.edu.epn.triplog.AdminViaje;
import ec.edu.epn.triplog.HomeActivity;
import ec.edu.epn.triplog.R;
import epn.edu.ec.triplog.vo.Viaje;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorViaje extends ArrayAdapter implements PopupMenu.OnMenuItemClickListener{

    private Viaje[] viaje;
    private Viaje vi;
    private HomeActivity homeActivity;


    public AdaptadorViaje(Context context, Viaje[] viaje, HomeActivity homeActivity) {
        super(context, android.R.layout.simple_expandable_list_item_1, viaje);
        this.viaje = viaje;
        this.homeActivity=homeActivity;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        vi = viaje[position];
        //converView representa toda la fila del item
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.lv_viajes, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tv_lugarViaje);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vi = viaje[position];
                Intent intent=new Intent(getContext(),AdminHistorias.class);
                intent.putExtra("idViaje",vi.getIdViaje());
                getContext().startActivity(intent);
            }
        });
        //Image img= (Image)convertView.findViewById(R.id.img_viaje);
        final ImageView iv=(ImageView)convertView.findViewById(R.id.iv_favorito);
        TextView tv1 = (TextView) convertView.findViewById(R.id.tv_descViaje);

        tv.setText(viaje[position].getLugarViaje());

        tv1.setText(viaje[position].getDescripcionViaje());

        if(viaje[position].isFavoritoViaje()){
            iv.setImageResource(R.drawable.ic_favorito);
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Viaje viajeActual = new Viaje();
                viajeActual = viaje[position];
                if (viajeActual.isFavoritoViaje()) {
                    viajeActual.setFavoritoViaje(false);
                    iv.setImageResource(R.drawable.ic_nofavorito);
                } else {
                    viajeActual.setFavoritoViaje(true);
                    iv.setImageResource(R.drawable.ic_favorito);
                    Toast.makeText(getContext(), "Viaje agregado a favoritos", Toast.LENGTH_SHORT).show();
                }
                //viajeActual.save();
            }

        });
        final ImageView ivMenu=(ImageView)convertView.findViewById(R.id.iv_more);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vi = viaje[position];
                PopupMenu popup = new PopupMenu(getContext(),view);
                popup.setOnMenuItemClickListener(AdaptadorViaje.this);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_lv_viaje,popup.getMenu());
                popup.show();
            }
        });

        return convertView;

    }

    public void abrirHistorias(View v){

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_equipaje:
                Intent intent=new Intent(getContext(),AdminEquipaje.class);
                intent.putExtra("idViaje",vi.getIdViaje());
                getContext().startActivity(intent);
                return true;
            case R.id.action_editar_viaje:
                Intent intent1= new Intent(getContext(),AdminViaje.class);
                intent1.putExtra("idViaje", vi.getIdViaje());
                getContext().startActivity(intent1);
                return true;
            case R.id.action_eliminar_viaje:
                vi.setActivo(false);
                //vi.save();
                Toast.makeText(getContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
                this.notifyDataSetChanged();
                homeActivity.actualizar();
                return true;
            default:
                return false;
        }
    }
}
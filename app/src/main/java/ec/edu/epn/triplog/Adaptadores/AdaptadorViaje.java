package ec.edu.epn.triplog.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
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

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import ec.edu.epn.triplog.AdminEquipaje;
import ec.edu.epn.triplog.AdminHistorias;
import ec.edu.epn.triplog.AdminViaje;
import ec.edu.epn.triplog.HomeActivity;
import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import epn.edu.ec.triplog.vo.Viaje;

/**
 * Created by ASUS R454LA on 11/12/2016.
 */

public class AdaptadorViaje extends ArrayAdapter implements PopupMenu.OnMenuItemClickListener{

    private Viaje[] viaje;
    private Viaje vi;
    private HomeActivity homeActivity;
    private int idViaje=0;


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
        TextView tv1 = (TextView) convertView.findViewById(R.id.tv_descViaje);
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
                new ViajeFavoritoAsync().execute(viajeActual);
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
//                vi = viaje[position];
                idViaje=vi.getIdViaje();
                Intent intent1= new Intent(getContext(),AdminViaje.class);
                intent1.putExtra("idViaje", vi.getIdViaje());
                intent1.putExtra("idUsuario", vi.getUsuario().getIdUsuario());
                intent1.putExtra("lugarViaje", vi.getLugarViaje());
                intent1.putExtra("descripcionViaje", vi.getDescripcionViaje());
                getContext().startActivity(intent1);
                return true;
            case R.id.action_eliminar_viaje:
                //vi.setActivo(false);
                //System.out.print(idViaje);

                new ViajeEliminarAsync().execute(vi);
                //vi.save();
                Toast.makeText(getContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
                this.notifyDataSetChanged();
                homeActivity.actualizar();
                return true;
            default:
                return false;
        }
    }


    public class ViajeEliminarAsync extends AsyncTask<Viaje, Void, String> {
        @Override
        protected String doInBackground(Viaje... viajes) {
            Viaje viaje = viajes[0];
            //String str="UPDATE viaje SET lugar ='holahola', descripcion= 'mrhmrh'WHERE idUsuario = 1 and idViaje=23";
            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "eliminarPorId?idViaje={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
//
            valores.put("var1",viaje.getIdViaje() );

            return restTemplate.getForObject(url, String.class, valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();

        }
    }


    public class ViajeFavoritoAsync extends AsyncTask<Viaje, Void, String> {
        @Override
        protected String doInBackground(Viaje... viajes) {
            Viaje viaje = viajes[0];
            //String str="UPDATE viaje SET lugar ='holahola', descripcion= 'mrhmrh'WHERE idUsuario = 1 and idViaje=23";
            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "favoritoPorId?idViaje={var1}&favorito={var2}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
//
            valores.put("var1",viaje.getIdViaje() );
            valores.put("var2",viaje.isFavoritoViaje() );

            return restTemplate.getForObject(url, String.class, valores);
        }

        @Override
        protected void onPostExecute(String s) {


        }
    }
}
package ec.edu.epn.triplog.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import ec.edu.epn.triplog.AdminHistorias;
import ec.edu.epn.triplog.R;
import ec.edu.epn.triplog.RegHistorias;
import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
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
                /*---CON SQLITE---*/
                //hist.setActivo(false);
                //hist.save();
                new HistoriaEliminarAsync().execute(hist);
                Toast.makeText(getContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
                this.notifyDataSetChanged();
                adminHistorias.actualizarHistorias();
                return true;
            default:
                return false;
        }
    }

    public class HistoriaEditarAsync extends AsyncTask<Historia,Void,String> {

        @Override
        protected String doInBackground(Historia... historias) {
            Historia historia = historias[0];

            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminHistoria/" +
                    "modificar?idHistoria={var1}&nombre={var2}&descripcion={var3}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1",historia.getId());
            valores.put("var2",historia.getNombre());
            valores.put("var3",historia.getDescripcion());

            return restTemplate.getForObject(url, String.class, valores);
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(), "Registro modificado", Toast.LENGTH_SHORT).show();
        }
    }

    public class HistoriaEliminarAsync extends AsyncTask<Historia, Void, String> {
        @Override
        protected String doInBackground(Historia... historias) {
            Historia historia = historias[0];
            //String str="UPDATE viaje SET lugar ='holahola', descripcion= 'mrhmrh'WHERE idUsuario = 1 and idViaje=23";
            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminHistoria/" +
                    "eliminarPorId?idHistoria={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
//
            valores.put("var1",historia.getId() );

            return restTemplate.getForObject(url, String.class, valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
        }
    }
}
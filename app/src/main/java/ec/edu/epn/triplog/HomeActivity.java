package ec.edu.epn.triplog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.edu.epn.triplog.Adaptadores.AdaptadorViaje;
import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import epn.edu.ec.triplog.vo.Usuario;
import epn.edu.ec.triplog.vo.Viaje;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Viaje datos[] = new Viaje[3];
    ListView lv_viajes;
    private Usuario usuario;
    private TextView txvNombreUsuario;
    private TextView txvEmailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*usuario=Usuario.getById(getIntent().getLongExtra("idUsuario",0));
        txvNombreUsuario =(TextView) findViewById(R.id.txvNombreUsuario);
        txvEmailUsuario =(TextView) findViewById(R.id.txvEmailUsuario);*/


        new UserInformationAsync().execute(getIntent().getStringExtra("usuario"));


        /*List<Viaje> lstViajes=Viaje.getFavoritesByUserId(usuario);

        AdaptadorViaje av = new AdaptadorViaje(this,lstViajes.toArray(new Viaje[lstViajes.size()]),this);
        lv_viajes.setAdapter(av);*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(usuario!=null) {
            new ViajesUsuarioAsync().execute(usuario.getIdUsuario());
        }

        /*List<Viaje> lstViajes=Viaje.getAllByUseurId(usuario);
        AdaptadorViaje av = new AdaptadorViaje(this,lstViajes.toArray(new Viaje[lstViajes.size()]),this);
        lv_viajes.setAdapter(av);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.perfil) {
            Intent intent = new Intent(this, UserRegisterActivity.class);
            intent.putExtra("idUsuario",usuario.getIdUsuario());
            startActivity(intent);
        } else if (id == R.id.misViajes) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("idUsuario",usuario.getIdUsuario());
            startActivity(intent);

        } else if (id == R.id.favoritos) {

        } else if (id == R.id.listaDeseos) {
            Intent intent = new Intent(this, WishList.class);
            startActivity(intent);
        } else if (id == R.id.compartir) {

        } else if (id == R.id.acercaDe) {

        } else if (id == R.id.configuracion) {

        } else if (id == R.id.feedback) {
            Intent intent = new Intent(this, Feedback.class);
            intent.putExtra("idUsuario",usuario.getIdUsuario());
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void actualizar (){
        /*List<Viaje> lstViajes=Viaje.getAllByUseurId(usuario);
        AdaptadorViaje av = new AdaptadorViaje(this,lstViajes.toArray(new Viaje[lstViajes.size()]),this);
        lv_viajes.setAdapter(av);*/
        new  ViajesUsuarioAsync().execute(usuario.getIdUsuario());
    }

    public void setAdaptadorViaje(List<Viaje> viajes){
        AdaptadorViaje av = new AdaptadorViaje(this,viajes.toArray(new epn.edu.ec.triplog.vo.Viaje[viajes.size()]),this);
        lv_viajes.setAdapter(av);
    }


    public void setControlerConf(){
        lv_viajes = (ListView) findViewById(R.id.lv_viajes);
        lv_viajes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,AdminViaje.class);
                intent.putExtra("idUsuario",usuario.getIdUsuario());
                startActivity(intent);
            }
        });
    }

    public class UserInformationAsync extends AsyncTask<String,Void,List<epn.edu.ec.triplog.vo.Usuario>> {
        @Override
        protected List<epn.edu.ec.triplog.vo.Usuario> doInBackground(String... strings) {

            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminUsuario/" +
                    "consultarPorUsuario?usuario={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", strings[0]);
            ResponseEntity<epn.edu.ec.triplog.vo.Usuario[]> responseEntity = restTemplate.getForEntity(url, epn.edu.ec.triplog.vo.Usuario[].class, valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<epn.edu.ec.triplog.vo.Usuario> s) {
            usuario=s.get(0);
            txvNombreUsuario =(TextView) findViewById(R.id.txvNombreUsuario);
            txvEmailUsuario =(TextView) findViewById(R.id.txvEmailUsuario);
            setControlerConf();
            new  ViajesUsuarioAsync().execute(usuario.getIdUsuario());
        }
    }

    public class ViajesUsuarioAsync extends AsyncTask<Integer,Void,List<epn.edu.ec.triplog.vo.Viaje>> {

        @Override
        protected List<epn.edu.ec.triplog.vo.Viaje> doInBackground(Integer... args) {

            final String url = "http://" + VariblesGlobales.IP + ":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminViaje/" +
                    "consultarPorIdUsuario?idUsuario={var1}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String, Object> valores = new HashMap<>();
            valores.put("var1", args[0]);
            ResponseEntity<epn.edu.ec.triplog.vo.Viaje[]> responseEntity = restTemplate.getForEntity(url, epn.edu.ec.triplog.vo.Viaje[].class, valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<epn.edu.ec.triplog.vo.Viaje> s) {
            setAdaptadorViaje(s);
        }
    }
}

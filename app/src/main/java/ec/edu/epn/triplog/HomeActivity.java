package ec.edu.epn.triplog;

import android.content.Intent;
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

import java.util.List;

import ec.edu.epn.triplog.Adaptadores.AdaptadorViaje;
import ec.edu.epn.triplog.vo.Usuario;
import ec.edu.epn.triplog.vo.Viaje;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Viaje datos[] = new Viaje[3];
    ListView lv_viajes;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usuario=Usuario.getById(getIntent().getLongExtra("idUsuario",0));

        lv_viajes = (ListView) findViewById(R.id.lv_viajes);
        lv_viajes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        List<Viaje> lstViajes=Viaje.getFavoritesByUserId(usuario);

        AdaptadorViaje av = new AdaptadorViaje(this,lstViajes.toArray(new Viaje[lstViajes.size()]),this);
        lv_viajes.setAdapter(av);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,AdminViaje.class);
                intent.putExtra("idUsuario",usuario.getId());
                startActivity(intent);
            }
        });

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
        List<Viaje> lstViajes=Viaje.getAllByUseurId(usuario);
        AdaptadorViaje av = new AdaptadorViaje(this,lstViajes.toArray(new Viaje[lstViajes.size()]),this);
        lv_viajes.setAdapter(av);
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
            // Handle the camera action
        } else if (id == R.id.misViajes) {
            Intent intent = new Intent(this, HomeActivity.class);
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
            intent.putExtra("idUsuario",usuario.getId());
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void actualizar (){
        List<Viaje> lstViajes=Viaje.getAllByUseurId(usuario);
        AdaptadorViaje av = new AdaptadorViaje(this,lstViajes.toArray(new Viaje[lstViajes.size()]),this);
        lv_viajes.setAdapter(av);
    }
}

package ec.edu.epn.triplog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ec.edu.epn.triplog.vo.Usuario;
import ec.edu.epn.triplog.vo.Viaje;

public class AdminViaje extends AppCompatActivity {

    private Usuario usuario;
    private EditText edtNombreViaje;
    private EditText edtDescripcion;
    private Spinner spnPaisViaje;
    private Viaje viaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_viaje);
        usuario= Usuario.getById(getIntent().getLongExtra("idUsuario",0));
        edtNombreViaje =(EditText)findViewById(R.id.edtNombreViaje);
        edtDescripcion =(EditText)findViewById(R.id.edtDescripcionViaje);
        viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));
        if(viaje!=null){
            ((EditText) findViewById(R.id.edtNombreViaje)).setText(viaje.getLugarViaje());
            ((EditText) findViewById(R.id.edtDescripcionViaje)).setText(viaje.getDescripcionViaje());
        }

    }

    public void guardarViaje(View v){
        if(edtNombreViaje.getText().toString().isEmpty()||edtDescripcion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }
        if(viaje==null){
            viaje=new Viaje();
            viaje.setUsuario(usuario);
        }
        viaje.setNombre(edtNombreViaje.getText().toString());
        viaje.setDescripcionViaje(edtDescripcion.getText().toString());
        viaje.setLugarViaje(edtNombreViaje.getText().toString());
        viaje.setFavoritoViaje(false);
        viaje.setActivo(true);

        viaje.save();
        Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
        finish();
    }

}

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_viaje);
        usuario= Usuario.getById(getIntent().getLongExtra("idUsuario",0));
        edtNombreViaje =(EditText)findViewById(R.id.edtNombreViaje);
        edtDescripcion =(EditText)findViewById(R.id.edtDescripcionViaje);

    }

    public void guardarViaje(View v){
        if(edtNombreViaje.getText().toString().isEmpty()||edtDescripcion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }
        Viaje viaje=new Viaje();
        viaje.setNombre(edtNombreViaje.getText().toString());
        viaje.setDescripcionViaje(edtDescripcion.getText().toString());
        viaje.setLugarViaje(edtNombreViaje.getText().toString());
        viaje.setUsuario(usuario);
        viaje.setFavoritoViaje(false);
        viaje.setActivo(true);

        viaje.save();
        Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
        finish();
    }

}

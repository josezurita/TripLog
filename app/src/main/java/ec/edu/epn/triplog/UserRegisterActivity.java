package ec.edu.epn.triplog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.epn.triplog.vo.Usuario;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtNombre;
    private EditText edtEmail;
    private EditText edtContrasena;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        edtUsuario=(EditText)findViewById(R.id.edtUsuario);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtContrasena=(EditText)findViewById(R.id.edtClave);
        usuario=Usuario.getById(getIntent().getLongExtra("idUsuario",0));
        if(usuario!=null){
            edtUsuario.setText(usuario.getUsuario());
            edtNombre.setText(usuario.getNombre());
            edtEmail.setText(usuario.getEmail());
            edtContrasena.setText(usuario.getContrasena());
        }
    }

    public void guardarUsuario(View v){
        if(edtUsuario.getText().toString().isEmpty()||edtNombre.getText().toString().isEmpty()||
                edtEmail.getText().toString().isEmpty()||edtContrasena.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usr =Usuario.getByUsuario(edtUsuario.getText().toString());

        if(usr!=null){
            Toast.makeText(getApplicationContext(), "Usuario ya existe", Toast.LENGTH_SHORT).show();
            return;
        }
        if(usuario==null){
            usr=new Usuario();
        }else {
            usr = usuario;
        }
        usr.setUsuario(edtUsuario.getText().toString().trim());
        usr.setNombre(edtNombre.getText().toString().trim());
        usr.setEmail(edtEmail.getText().toString().trim());
        usr.setContrasena(edtContrasena.getText().toString().trim());
        usr.setActivo(true);
        usr.save();
        Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
        finish();
    }


}

package ec.edu.epn.triplog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import ec.edu.epn.triplog.vo.Usuario;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtNombre;
    private EditText edtEmail;
    private EditText edtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        edtUsuario=(EditText)findViewById(R.id.edtUsuario);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtContrasena=(EditText)findViewById(R.id.edtClave);
    }

    public void guardarUsuario(View v){
        Usuario usr =new Usuario();
        usr.usuario=edtUsuario.getText().toString();
        usr.nombre=edtNombre.getText().toString();
        usr.email=edtEmail.getText().toString();
        usr.contrasena=edtContrasena.getText().toString();
        usr.save();

    }


}

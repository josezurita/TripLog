package ec.edu.epn.triplog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.epn.triplog.vo.Usuario;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;
    private EditText edtUsuario;
    private EditText edtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnLogin=(Button)findViewById(R.id.login);
        edtUsuario=(EditText)findViewById(R.id.username);
        edtContrasena=(EditText)findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario=Usuario.getByUsuario(edtUsuario.getText().toString());
                if(edtUsuario.getText().toString().isEmpty()||edtContrasena.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingrese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(usuario==null){
                    Toast.makeText(getApplicationContext(), "Usuario: "+edtUsuario.getText().toString()+" no existe", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!edtContrasena.getText().toString().equals(usuario.contrasena)) {
                    Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        btnRegister=(Button)findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,UserRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

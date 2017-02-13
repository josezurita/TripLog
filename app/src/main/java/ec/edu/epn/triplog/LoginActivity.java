package ec.edu.epn.triplog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import epn.edu.ec.triplog.vo.Usuario;

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
                if(edtUsuario.getText().toString().isEmpty()||edtContrasena.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingrese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                new UserVerifyAsync().execute(edtUsuario.getText().toString());
            }
        });

        btnRegister=(Button)findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new PruebaRest().execute("hola");
                Intent intent=new Intent(LoginActivity.this,UserRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public class UserVerifyAsync extends AsyncTask<String,Void,List<epn.edu.ec.triplog.vo.Usuario>> {
        @Override
        protected List<epn.edu.ec.triplog.vo.Usuario> doInBackground(String... strings) {

            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminUsuario/" +
                    "consultarPorUsuario?usuario={var1}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",strings[0]);
            ResponseEntity<epn.edu.ec.triplog.vo.Usuario[]> responseEntity = restTemplate.getForEntity(url, epn.edu.ec.triplog.vo.Usuario[].class,valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<epn.edu.ec.triplog.vo.Usuario> s) {

            if(s.isEmpty()){
                Toast.makeText(getApplicationContext(), "Usuario: "+edtUsuario.getText().toString()+" no existe", Toast.LENGTH_SHORT).show();
                return;
            }
            Usuario u=s.get(0);
            if(!edtContrasena.getText().toString().equals(u.getContrasena())) {
                Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            intent.putExtra("idUsuario",u.getIdUsuario());
            startActivity(intent);
        }
    }
}

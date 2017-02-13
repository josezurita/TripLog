package ec.edu.epn.triplog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.edu.epn.triplog.Utilitarios.VariblesGlobales;
import epn.edu.ec.triplog.vo.Usuario;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtNombre;
    private EditText edtEmail;
    private EditText edtContrasena;
    private Usuario usuario;
    private boolean procesoCorriendo=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        edtUsuario=(EditText)findViewById(R.id.edtUsuario);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtContrasena=(EditText)findViewById(R.id.edtClave);
        //System.out.println(getIntent().getStringExtra("usuario"));
        new VerifyIfEditAsync().execute(getIntent().getStringExtra("usuario"));
        /*usuario= Usuario.getById(getIntent().getLongExtra("idUsuario",0));
        if(usuario!=null){
            edtUsuario.setText(usuario.getUsuario());
            edtNombre.setText(usuario.getNombre());
            edtEmail.setText(usuario.getEmail());
            edtContrasena.setText(usuario.getContrasena());
        }*/
    }

    public void guardarUsuario(View v){
        if(edtUsuario.getText().toString().isEmpty()||edtNombre.getText().toString().isEmpty()||
                edtEmail.getText().toString().isEmpty()||edtContrasena.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(usuario==null) {
            new UserVerifyAsync().execute(edtUsuario.getText().toString());
        }else{
            usuario.setUsuario(edtUsuario.getText().toString().trim());
            usuario.setNombre(edtNombre.getText().toString().trim());
            usuario.setEmail(edtEmail.getText().toString().trim());
            usuario.setContrasena(edtContrasena.getText().toString().trim());
            new  UserEditAsync().execute(usuario);
        }

        /*while(procesoCorriendo){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        //usr.save();
        Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
        finish();
        */
    }


    public class UserVerifyAsync extends AsyncTask<String,Void,List<Usuario>> {
        @Override
        protected List<Usuario> doInBackground(String... strings) {
            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminUsuario/" +
                    "consultarPorUsuario?usuario={var1}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",strings[0]);
            ResponseEntity<Usuario[]> responseEntity = restTemplate.getForEntity(url, Usuario[].class,valores);
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<Usuario> s) {

            if(!s.isEmpty()){
                Toast.makeText(getApplicationContext(), "Usuario ya existe "+s.get(0).getUsuario(), Toast.LENGTH_SHORT).show();
                return;
            }
            Usuario usr=new Usuario();
            usr.setUsuario(edtUsuario.getText().toString().trim());
            usr.setNombre(edtNombre.getText().toString().trim());
            usr.setEmail(edtEmail.getText().toString().trim());
            usr.setContrasena(edtContrasena.getText().toString().trim());
            usr.setActivo(true);
            new UserRegisterAsync().execute(usr);
        }
    }

    public class UserRegisterAsync extends AsyncTask<Usuario,Void,String> {
        @Override
        protected String doInBackground(Usuario... usuarios) {

            Usuario usr=usuarios[0];
            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminUsuario/" +
                    "insertar?usuario={var1}&nombre={var2}&email={var3}&contrasena={var4}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",usr.getUsuario());
            valores.put("var2",usr.getNombre());
            valores.put("var3",usr.getEmail());
            valores.put("var4",usr.getContrasena());
            return restTemplate.getForObject(url,String.class,valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro ingresado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public class VerifyIfEditAsync extends AsyncTask<String,Void,List<epn.edu.ec.triplog.vo.Usuario>> {
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
            if(!s.isEmpty()){
                usuario=s.get(0);
                edtUsuario.setText(usuario.getUsuario());
                edtNombre.setText(usuario.getNombre());
                edtEmail.setText(usuario.getEmail());
                edtContrasena.setText(usuario.getContrasena());
            }
        }
    }

    public class UserEditAsync extends AsyncTask<Usuario,Void,String> {
        @Override
        protected String doInBackground(Usuario... usuarios) {

            Usuario usr=usuarios[0];
            final String url="http://"+ VariblesGlobales.IP+":8080/AAM-Servicios-1.0-SNAPSHOT/rest/AdminUsuario/" +
                    "actualizar?usuario={var1}&nombre={var2}&email={var3}&contrasena={var4}&idUsuario={var5}";
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HashMap<String,Object> valores=new HashMap<>();
            valores.put("var1",usr.getUsuario());
            valores.put("var2",usr.getNombre());
            valores.put("var3",usr.getEmail());
            valores.put("var4",usr.getContrasena());
            valores.put("var5",usr.getIdUsuario());
            return restTemplate.getForObject(url,String.class,valores);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Registro modificado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

package ec.edu.epn.triplog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.epn.triplog.vo.Historia;
import ec.edu.epn.triplog.vo.Viaje;

public class RegHistorias extends AppCompatActivity {

    private Viaje viaje;
    private EditText etNombreH;
    private EditText etDescH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_historias);
        viaje= Viaje.getById(getIntent().getLongExtra("idViaje",0));
        etNombreH=(EditText)findViewById(R.id.et_nombreH);
        etDescH=(EditText)findViewById(R.id.et_descH);
    }

     public void guardarHistoria(View v){
         if(etNombreH.getText().toString().isEmpty()||etDescH.getText().toString().isEmpty()){
             Toast.makeText(getApplicationContext(), "Ingrese los datos", Toast.LENGTH_SHORT).show();
             return;
         }
         Historia historia = new Historia();
         historia.setActivo(true);
         historia.setNombre(etNombreH.getText().toString().trim());
         historia.setDescripcion(etDescH.getText().toString().trim());
         historia.setViaje(viaje);
         historia.save();
         etNombreH.setText("");
         etDescH.setText("");

         Toast.makeText(getApplicationContext(), "Equipaje ingresado", Toast.LENGTH_SHORT).show();
         finish();
     }
}

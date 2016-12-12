package ec.edu.epn.triplog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ec.edu.epn.triplog.vo.Comentario;

public class Feedback extends AppCompatActivity {

    private TextView edtDescripcionFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        edtDescripcionFeed=(EditText) findViewById(R.id.edtDescripcionFeed);
    }

    public void guardarFeedback(View v){
        if(edtDescripcionFeed.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese una descripcion", Toast.LENGTH_SHORT).show();
            return;
        }
        Comentario comentario = new Comentario();
        comentario.setActivo(true);
        comentario.setDescripcion(edtDescripcionFeed.getText().toString().trim());
        comentario.save();
        edtDescripcionFeed.setText("");
        Toast.makeText(getApplicationContext(), "Comentario registrado", Toast.LENGTH_SHORT).show();
        finish();
    }
}

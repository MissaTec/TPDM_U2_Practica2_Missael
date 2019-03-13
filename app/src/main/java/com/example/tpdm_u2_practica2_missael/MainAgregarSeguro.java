package com.example.tpdm_u2_practica2_missael;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainAgregarSeguro extends AppCompatActivity {
    EditText descripcionSeguro,fechaSeguro;
    Spinner tipoSeguro;
    Button agregarSeguro,regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agregar_seguro);
        descripcionSeguro = findViewById(R.id.descripcionseguro);
        fechaSeguro = findViewById(R.id.fechaseguro);
        tipoSeguro = findViewById(R.id.tiposeguro);
        agregarSeguro = findViewById(R.id.agregarseguro);
        regresar = findViewById(R.id.regresar);


        agregarSeguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertar(){
        String mensaje;
        Seguro s = new Seguro(this);
        Bundle parametros = getIntent().getExtras();
        boolean respuesta = s.insertar(new Seguro(0,descripcionSeguro.getText().toString(),
                fechaSeguro.getText().toString(),tipoSeguro.getSelectedItem().toString(),
                parametros.getString("telefono")));
        if(respuesta){
            mensaje = "Se agrego seguro a "+parametros.getString("nombre");
            limpiar();
        }else{
            mensaje = "Error, no se puedo insertar seguro "+s.error;
        }

        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

    private void limpiar(){
        descripcionSeguro.setText("");
        fechaSeguro.setText("");
    }

}

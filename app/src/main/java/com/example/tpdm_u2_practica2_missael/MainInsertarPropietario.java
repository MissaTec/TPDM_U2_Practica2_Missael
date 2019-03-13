package com.example.tpdm_u2_practica2_missael;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainInsertarPropietario extends AppCompatActivity {

    EditText telefono,nombre,domicilio,fecha,descripcion;
    Spinner tipo;
    Button insetarPropietario,regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_insertar_propietario);
        telefono = findViewById(R.id.telefonopropietario);
        nombre = findViewById(R.id.nombrepropietario);
        domicilio = findViewById(R.id.domiciliopropietario);
        fecha = findViewById(R.id.fechapropietario);
        descripcion = findViewById(R.id.descripcionseguro);
        tipo = findViewById(R.id.tiposeguro);
        insetarPropietario = findViewById(R.id.agragarpropietario);
        regresar = findViewById(R.id.regresar);

        insetarPropietario.setOnClickListener(new View.OnClickListener() {
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



    public void insertar(){
        String menseaje;
        Propietario p = new Propietario(this);
        Seguro s = new Seguro(this);
        boolean respuesta1 = p.insertar(new Propietario(telefono.getText().toString(),nombre.getText().toString(),
                domicilio.getText().toString(),fecha.getText().toString()));
        boolean respuesta2 = s.insertar(new Seguro(0,descripcion.getText().toString(),fecha.getText().toString(),
                tipo.getSelectedItem().toString(),telefono.getText().toString()));
        if(respuesta1 && respuesta2){
            menseaje = "Se realizo la insercion con exito :)";
            limpiar();
        }else{
            menseaje = "Ocurrio un error en algun punto de la insercion :(";
        }

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("aTENCION");
        alerta.setMessage(menseaje);
        alerta.setPositiveButton("OK",null);
        alerta.show();
    }


    public void  limpiar(){
        telefono.setText("");
        nombre.setText("");
        descripcion.setText("");
        fecha.setText("");
        domicilio.setText("");
    }

}

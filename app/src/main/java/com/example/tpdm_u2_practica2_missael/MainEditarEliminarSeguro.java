package com.example.tpdm_u2_practica2_missael;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainEditarEliminarSeguro extends AppCompatActivity {
    EditText consultaTelefono;
    Button buscar,regresar;
    ListView listaSeguro;
    Seguro seguro[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_editar_eliminar_seguro);
        consultaTelefono = findViewById(R.id.consultaTelefono);
        buscar =findViewById(R.id.buscar);
        regresar = findViewById(R.id.regresar);
        listaSeguro = findViewById(R.id.listaseguro);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarSeguro();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaSeguro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertaAccionSeguro(position);

            }
        });
    }

    private void alertaAccionSeguro(final int pos){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION");
        alerta.setMessage("Desea eliminar este seguro?");
        alerta.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarSeguro(pos);
            }
        });
        alerta.setNegativeButton("NO",null);
        alerta.show();

    }


    private void eliminarSeguro(int pos){
        Seguro s = new Seguro(this);
        String mensaje;
        boolean respuesta = s.eliminar(new Seguro(seguro[pos].getId(),seguro[pos].getDescripcion(),
                                            seguro[pos].getFecha(),seguro[pos].getTipo(),seguro[pos].getTelefono()));
        if(respuesta){
            mensaje = "Se elimino el seguro correctamente";
        }else{
            mensaje = "ERROR, NO SE PUEDO ELIMINAR EL SEGURO";
        }
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
        buscarSeguro();
    }



    private void buscarSeguro(){
        Seguro s = new Seguro(this);
        seguro = s.consultar(consultaTelefono.getText().toString());
        String[] datos = null;
        if(seguro == null){
            datos = new String[1];
            datos[0] = "No se encontraron seguros asociados a este telefono";
        }else{
            datos = new String[seguro.length];
            for(int i = 0;i<seguro.length;i++){
                Seguro temp = seguro[i];
                datos[i] = temp.getDescripcion()+"\n"+
                        temp.getTipo()+"\n"+temp.getFecha();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datos);
        listaSeguro.setAdapter(adapter);
    }
}

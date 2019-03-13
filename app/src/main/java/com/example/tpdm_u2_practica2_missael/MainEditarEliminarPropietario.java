package com.example.tpdm_u2_practica2_missael;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainEditarEliminarPropietario extends AppCompatActivity {
    EditText telefono,nombre,domicilio,fecha;
    Button editarPropietario,eliminarPropietario,regresar;
    ListView listaPropietarios;
    Propietario propietario[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_editar_eliminar_propietario);
        telefono = findViewById(R.id.editartelefonopropietario);
        nombre = findViewById(R.id.editarnombrepropietario);
        domicilio = findViewById(R.id.editardomiciliopropietario);
        fecha = findViewById(R.id.fechapropietario);

        fecha.setFocusable(false);
        telefono.setFocusable(false);

        editarPropietario = findViewById(R.id.editarpropietario);
        eliminarPropietario = findViewById(R.id.eliminarpropietario);
        regresar = findViewById(R.id.regresar);

        editarPropietario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
                finish();
            }
        });

        eliminarPropietario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
                finish();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listaPropietarios = findViewById(R.id.listapropietarios);

        listaPropietarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                telefono.setText(propietario[position].getTelefono());
                nombre.setText(propietario[position].getNombre());
                domicilio.setText(propietario[position].getDomicilio());
                fecha.setText(propietario[position].getFecha());

            }
        });
    }

    @Override
    protected void onStart() {
        Propietario p = new Propietario(this);
        propietario = p.consultar();
        String datos[] = null;
        if(propietario == null){
            datos = new String[1];
            datos[0] = "No hay propietarios";
        }else{
            datos = new String[propietario.length];
            for(int i = 0; i<propietario.length; i++){
                Propietario temp = propietario[i];
                datos[i] = temp.getNombre()+"\n"+temp.getTelefono();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datos);
        listaPropietarios.setAdapter(adapter);
        super.onStart();
    }

    private void actualizar(){
        Propietario p = new Propietario(this);
        String mensaje;
        boolean respuesta = p.actualizar(new Propietario(telefono.getText().toString(),nombre.getText().toString(),
                domicilio.getText().toString(),fecha.getText().toString()));
        if(respuesta){
            mensaje = "Se edito el propietario con exito";
        }else{
            mensaje = "ERROR, ALGO SALIO MAL";
        }
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

    private void eliminar(){
        Propietario p = new Propietario(this);
        Seguro s = new Seguro(this);
        String mensaje;
        Seguro seguro[] = s.consultar(telefono.getText().toString());
        if(seguro == null){
            boolean respuesta2 = p.eliminar(new Propietario(telefono.getText().toString(),nombre.getText().toString(),
                    domicilio.getText().toString(),fecha.getText().toString()));
            if(respuesta2){
                mensaje = "Se elimino al propietario";
            }else{
                mensaje = "ERROR, ALGO SALIO MAL";
            }
        }else{
        boolean respuesta1 = s.eliminar(new Propietario(telefono.getText().toString(),nombre.getText().toString(),
                domicilio.getText().toString(),fecha.getText().toString()));
        boolean respuesta2 = p.eliminar(new Propietario(telefono.getText().toString(),nombre.getText().toString(),
                domicilio.getText().toString(),fecha.getText().toString()));
        if(respuesta1 && respuesta2){
            mensaje = "Se elimino al propietario";
        }else{
            mensaje = "ERROR, ALGO SALIO MAL";
        }
        }
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}

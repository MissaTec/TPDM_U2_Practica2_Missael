package com.example.tpdm_u2_practica2_missael;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Propietario[] propietario;
    Seguro[] seguro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertaAgragarSeguro(position);
            }
        });
    }

    private void alertaAgragarSeguro(final int pos){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION");
        alerta.setMessage("Deseas agregar un nuevo seguro a "+propietario[pos].getNombre()+"?");
        alerta.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                invocarAgregarSeguro(pos);
            }
        });
        alerta.setNegativeButton("NO",null);
        alerta.show();
    }

    private void invocarAgregarSeguro(int pos){
        Intent agregarSeguro = new Intent(this,MainAgregarSeguro.class);
        agregarSeguro.putExtra("telefono",propietario[pos].getTelefono());
        agregarSeguro.putExtra("nombre",propietario[pos].getNombre());
        startActivity(agregarSeguro);
    }

    protected void onStart() {
        Propietario p = new Propietario(this);
        Seguro s = new Seguro(this);
        propietario = p.consultar();
        String[] datosPropietario = null;
        if(propietario == null) {
            datosPropietario = new String[1];
            datosPropietario[0] = "No existen registros";
        }else{
            datosPropietario = new String[propietario.length];
            for(int i = 0; i<propietario.length;i++){
                Propietario temp = propietario[i];
                seguro = s.consultar(temp.getTelefono());
                String[] datosSeguro;
                String seguroPropietario ="";
                if(seguro == null){
                    seguroPropietario = "Este propietario no tiene seguros";
                }else {
                    datosSeguro = new String[seguro.length];
                    for (int x = 0; x < datosSeguro.length; x++) {
                        Seguro temp2 = seguro[x];
                        datosSeguro[x] = "Datos del seguro " + (x + 1) + " :\n\n" +
                                temp2.getDescripcion() + "\n" +
                                temp2.getTipo() + "\n" +
                                temp2.getFecha() + "\n\n";
                        seguroPropietario += datosSeguro[x];
                    }
                }

             datosPropietario[i] = "Datos del propietario:\n\n"+
                     temp.getNombre()+"\n"+
                     temp.getTelefono()+"\n"+
                     temp.getDomicilio()+"\n"+
                     temp.getFecha()+"\n\n"+
                     seguroPropietario;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datosPropietario);
        lista.setAdapter(adapter);
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusito,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.nuevopropietario:
                Intent insertarPropietario = new Intent(MainActivity.this,MainInsertarPropietario.class);
                startActivity(insertarPropietario);
                break;
            case R.id.editareliminarpropietario:
                Intent editarEliminarPropietario = new Intent(this,MainEditarEliminarPropietario.class);
                startActivity(editarEliminarPropietario);
                break;
            case R.id.editareliminarseguro:
                Intent editarEliminarSeguro = new Intent(this,MainEditarEliminarSeguro.class);
                startActivity(editarEliminarSeguro);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}


package com.example.tpdm_u2_practica2_missael;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Propietario {
    private BaseDatos base;
    private String telefono,nombre,domicilio,fecha;
    protected String error;

    public Propietario (Activity activity){
        base = new BaseDatos(activity,"Aseguradora",null,1);
    }

    public Propietario(String telefono, String nombre, String domicilio,String fecha){
        this.telefono = telefono;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.fecha = fecha;
    }


    public boolean insertar (Propietario propietario){
        try{
            SQLiteDatabase insert = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("TELEFONO",propietario.getTelefono());
            datos.put("NOMBRE",propietario.getNombre());
            datos.put("DOMICILIO",propietario.getDomicilio());
            datos.put("FECHA",propietario.getFecha());

            long resultado = insert.insert("PROPIETARIO", null, datos);

            insert.close();

            if(resultado==-1){
                return false;
            }
        }catch(SQLiteException e){
            error = e.getMessage();
            return false;
        }
            return true;
    }

    public boolean actualizar (Propietario propietario){
        try{
            SQLiteDatabase actualiza = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("TELEFONO",propietario.getTelefono());
            datos.put("NOMBRE",propietario.getNombre());
            datos.put("DOMICILIO",propietario.getDomicilio());
            datos.put("FECHA",propietario.getFecha());
            String id[] = {propietario.getTelefono()};

            long resultado = actualiza.update("PROPIETARIO",datos,"TELEFONO=?",id);

            actualiza.close();
            if(resultado==-1){
                return false;
            }
        }catch(SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public boolean eliminar(Propietario propietario){
        int resultado;
        try{
            SQLiteDatabase elimina =base.getWritableDatabase();
            String id[] = {propietario.getTelefono()};
            resultado = elimina.delete("PROPIETARIO","TELEFONO=?",id);
            elimina.close();
        }catch(SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado>0;
    }

    public Propietario[] consultar(){
        Propietario[] resultado = null;
        try{
            SQLiteDatabase consulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROPIETARIO";
            Cursor c = consulta.rawQuery(SQL,null);
            if(c.moveToFirst()){
                resultado = new Propietario[c.getCount()];
                int pos = 0;
                do{
                    resultado[pos] = new Propietario(c.getString(0),c.getString(1),
                            c.getString(2),c.getString(3));
                    pos++;
                }while(c.moveToNext());
            }
            consulta.close();
        }catch (SQLiteException e){
            return null;
        }
        return resultado;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

package com.example.tpdm_u2_practica2_missael;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

public class Seguro {
    private BaseDatos base;
    private int id;
    private String descripcion,fecha,tipo,telefono;
    protected String error;

    public Seguro(Activity activity){
        base = new BaseDatos(activity,"Aseguradora",null,1);
    }

    public Seguro(int id,String descripcion, String fecha, String tipo, String telefono){
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public boolean insertar(Seguro seguro){
        try{
            SQLiteDatabase insert = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION",seguro.getDescripcion());
            datos.put("FECHA",seguro.getFecha());
            datos.put("TIPO",seguro.getTipo());
            datos.put("TELEFONO",seguro.getTelefono());

            long resultado = insert.insert("SEGURO","IDSEGURO",datos);

            insert.close();
            if(resultado ==-1){
                return false;
            }
        }catch(SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public boolean actualizar (Seguro seguro){
        try{
            SQLiteDatabase actualiza = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION",seguro.getDescripcion());
            datos.put("FECHA",seguro.getFecha());
            datos.put("TIPO",seguro.getTipo());
            datos.put("TELEFONO",seguro.getTelefono());
            String id[] = {""+seguro.getId()};
            long resultado = actualiza.update("Seguro",datos,"IDSEGURO=?",id);
            actualiza.close();
            if(resultado == -1){
                return false;
            }
        }catch(SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public boolean eliminar(Seguro seguro){
        int resultado;
        try{
        SQLiteDatabase elimina = base.getWritableDatabase();
        String id[] = {""+seguro.getId()};
        resultado = elimina.delete("SEGURO","IDSEGURO=?",id);
        elimina.close();
        }catch(SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado>0;
    }

    public boolean eliminar(Propietario propietario){
        int resultado;
        try{
            SQLiteDatabase elimina = base.getWritableDatabase();
            String telefono[] ={""+propietario.getTelefono()};
            resultado = elimina.delete("SEGURO","TELEFONO=?", telefono);
            elimina.close();
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado>0;
    }

    public Seguro[] consultar(String telefono){
        Seguro[] resultado = null;
        try{
            SQLiteDatabase consulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM SEGURO WHERE TELEFONO = '"+telefono+"'";
            Cursor c = consulta.rawQuery(SQL,null);
            if(c.moveToFirst()){
                resultado = new Seguro[c.getCount()];
                int pos = 0;
                do{
                    resultado[pos] = new Seguro(c.getInt(0),c.getString(1),c.getString(2),
                            c.getString(3), c.getString(4));
                    pos++;
                }while(c.moveToNext());
            }
            consulta.close();
        }catch(SQLiteException e){
            return null;
        }
        return resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

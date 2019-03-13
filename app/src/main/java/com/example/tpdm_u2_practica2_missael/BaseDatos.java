package com.example.tpdm_u2_practica2_missael;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {

    public BaseDatos(Context context,String name,SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PROPIETARIO(TELEFONO VARCHAR(20) PRIMARY KEY NOT NULL,"+
                "NOMBRE VARCHAR(200) NOT NULL, DOMICILIO VARCHAR(200), FECHA VARCHAR(200))");
        db.execSQL("CREATE TABLE SEGURO(IDSEGURO INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "DESCRIPCION VARCHAR(200) NOT NULL, FECHA VARCHAR(20), TIPO VARCHAR(200),"+
                "TELEFONO VARCHAR(20), FOREIGN KEY (TELEFONO) REFERENCES PROPIETARIO(TELEFONO))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

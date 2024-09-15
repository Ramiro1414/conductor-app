package com.example.conductor_app.backend.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.conductor_app.backend.Repository.PatenteRepository;
import com.example.conductor_app.backend.modelo.Patente;

import java.util.ArrayList;
import java.util.List;

public class PatenteService extends SQLiteOpenHelper implements PatenteRepository {
    Context contexto;

    private List<Patente> patentes = new ArrayList<Patente>();

    public PatenteService(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE patente (_id INTEGER PRIMARY KEY AUTOINCREMENT, caracteres TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public List<Patente> findAll() {
        patentes.clear(); // Limpiar la lista para evitar duplicados
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM patente ORDER BY caracteres ASC";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                patentes.add(new Patente(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return patentes;
    }


    @Override
    public Patente findById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM patente WHERE _id =" + id;
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraerPatente(cursor);
            else
                return null;
        } catch (Exception e){
            Log.d("TAG", "Error elemento(id): " + e.getMessage());
            throw(e);
        } finally {
            if(cursor != null) cursor.close();
        }
    }

    @Override
    public Patente findByCaracteres(String caracteres) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM patente WHERE caracteres =" + caracteres;
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraerPatente(cursor);
            else
                return null;
        } catch (Exception e){
            Log.d("TAG", "Error elemento(caracteres): " + e.getMessage());
            throw(e);
        } finally {
            if(cursor != null) cursor.close();
        }
    }

    private Patente extraerPatente(Cursor cursor) {
        Patente patente = new Patente();
        patente.setId( cursor.getInt( 0 ) );
        patente.setCaracteres(cursor.getString( 1 ));
        return patente;
    }

    @Override
    public void save(Patente patente) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("caracteres", patente.getCaracteres());
        database.insert("patente", null, values);
    }

    @Override
    public void deleteById(int id) {
        SQLiteDatabase database = getWritableDatabase(); // Cambia a writable
        String[] parametros = { String.valueOf(id) };

        int rowsAffected = database.delete("patente", "_id=?", parametros);
        database.close();

        if (rowsAffected == 0) {
            Log.d("TAG", "No se eliminó ninguna patente con el ID: " + id);
        } else {
            Log.d("TAG", "Patente con ID " + id + " eliminada correctamente.");
        }
    }


    @Override
    public void updateById(int id, Patente patenteNueva) {
        SQLiteDatabase database = getWritableDatabase(); // Asegúrate de usar getWritableDatabase
        String[] parametros = { String.valueOf(id) };

        ContentValues values = new ContentValues();
        values.put("caracteres", patenteNueva.getCaracteres());

        int rowsAffected = database.update("patente", values, "_id=?", parametros); // Asegúrate de pasar `values` como primer parámetro
        database.close();

        if (rowsAffected == 0) {
            Log.d("DASJDOASJDISAOJDOSAIDJSAODJSAIDOJAS", "No se actualizó ninguna patente con el ID: " + id);
        }
    }



}

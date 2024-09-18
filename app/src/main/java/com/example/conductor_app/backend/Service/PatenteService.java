package com.example.conductor_app.backend.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
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

    private List<Patente> patentes = new ArrayList<Patente> ();

    public PatenteService(@Nullable Context context, @Nullable String name){
        super(context, name, null, 2);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE patente (_id INTEGER PRIMARY KEY AUTOINCREMENT, caracteres TEXT UNIQUE)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Elimina la tabla anterior
        String dropTableSql = "DROP TABLE IF EXISTS patente";
        sqLiteDatabase.execSQL(dropTableSql);

        // Vuelve a crear la tabla llamando a onCreate
        onCreate(sqLiteDatabase);
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

    private String formatear(Patente patente) {
        return patente.getCaracteres().replace(" ", "").toUpperCase();

    }

    @Override
    public void save(Patente patente) throws PatenteRepetidaException {
        patente.setCaracteres(formatear(patente));
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM patente WHERE caracteres = '" + patente.getCaracteres() + "'";
        Cursor cursor = database.rawQuery(sql, null);

        // Verificar si la patente esta repetida dentro de la tabla
        if(cursor.getCount() != 0) {
            throw new PatenteRepetidaException("repetido");
        }else{
            ContentValues values = new ContentValues();
            values.put("caracteres", patente.getCaracteres());
            database.insert("patente", null, values);
            database.close();
        }
    }

    @Override
    public void updateById(Patente patenteNueva) throws PatenteRepetidaException {
        patenteNueva.setCaracteres(formatear(patenteNueva));
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String sql = "SELECT * FROM patente WHERE caracteres = '" + patenteNueva.getCaracteres() + "'";
        Cursor cursor = readableDatabase.rawQuery(sql, null);

        // Verificar si la patente esta repetida dentro de la tabla
        if(cursor.getCount() != 0) {
            throw new PatenteRepetidaException("repetido");
        }else{
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String[] parametros = { String.valueOf(patenteNueva.getId()) };
            ContentValues values = new ContentValues();
            values.put("caracteres", patenteNueva.getCaracteres());
            writableDatabase.update("patente", values, "_id=?", parametros);
            writableDatabase.close();
        }
    }

    @Override
    public void deleteById(int id) {
        SQLiteDatabase database = getWritableDatabase(); // Cambia a writable
        String[] parametros = {String.valueOf(id)};

        int rowsAffected = database.delete("patente", "_id=?", parametros);
        database.close();

        if (rowsAffected == 0) {
            Log.d("TAG", "No se eliminó ninguna patente con el ID: " + id);
        } else {
            Log.d("TAG", "Patente con ID " + id + " eliminada correctamente.");
        }
    }

}

package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.conductor_app.backend.modelo.LineaPoligono;
import com.example.conductor_app.backend.modelo.Poligono;
import com.example.conductor_app.backend.modelo.Punto;

@Database(entities = {LineaPoligono.class, Poligono.class, Punto.class}, version = 1)
public abstract class LineaPoligonoDataBase extends RoomDatabase {

    public abstract LineaPoligonoDao lineaPoligonoDao();

}

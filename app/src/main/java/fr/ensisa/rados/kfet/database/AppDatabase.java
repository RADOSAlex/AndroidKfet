package fr.ensisa.rados.kfet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fr.ensisa.rados.kfet.model.Product;
@TypeConverters({DatabaseTypeConverters.class})
@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    static public void create(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "kfet.db").build();
        }
    }

    static public AppDatabase get () {
        return instance;
    }

    public abstract ProductDao getProductDao ();
    public abstract KfetDao getKfetDao();

}
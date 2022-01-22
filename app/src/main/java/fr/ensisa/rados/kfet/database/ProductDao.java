package fr.ensisa.rados.kfet.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.ensisa.rados.kfet.model.Product;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    public LiveData<List<Product>> getAll ();

    @Query("SELECT * FROM products WHERE pid = :id")
    public LiveData<Product> getById (long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long upsert (Product product);

    @Delete
    public void delete (Product product);
}

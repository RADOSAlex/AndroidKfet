package fr.ensisa.rados.kfet.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.ensisa.rados.kfet.model.Kfet;
import fr.ensisa.rados.kfet.model.KfetProductAssociation;
import fr.ensisa.rados.kfet.model.Product;

@Dao
public interface KfetDao {
    @Query("SELECT * FROM products")
    public LiveData<List<Kfet>> getAll ();

    @Transaction
    @Query("SELECT * FROM kfets WHERE kid = :id")
    public LiveData<Kfet> getById (long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long upsert (Kfet kfet);

    @Delete
    public void delete (Kfet kfet);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addKfetProduct(KfetProductAssociation kfetProductAssociation);

    @Delete
    void removeKfetProduct(KfetProductAssociation product);

}

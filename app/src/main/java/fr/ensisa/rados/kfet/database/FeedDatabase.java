package fr.ensisa.rados.kfet.database;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.kfet.model.Kfet;
import fr.ensisa.rados.kfet.model.KfetProductAssociation;
import fr.ensisa.rados.kfet.model.Product;
import fr.ensisa.rados.kfet.model.ProductType;

public class FeedDatabase {

    private long [] feedProducts() {

        ProductDao dao = AppDatabase.get().getProductDao();
        long [] ids = new long[3];
        long id = 0;
        id =dao.upsert(new Product(0,"Pizza Salami", 3, ProductType.food, "Délicieuse Pizza", new Date(2022, 02, 01), 2));
        ids[0]=id;
        id=dao.upsert(new Product(0,"Pizza Chevre", 3, ProductType.food, "Délicieuse Pizza", new Date(2022, 02, 01), 2));
        ids[1]=id;
        id=dao.upsert(new Product(0,"Café", 3, ProductType.drinks, "Café crème", new Date(2023, 05, 01), 50));
        ids[2]=id;
        return ids;
    }

    private void feedKfet(long [] pids)
    {
        KfetDao dao = AppDatabase.get().getKfetDao();
        Kfet kfet = new Kfet("Lumiere", "Kfet Batiment Lumiere",  (int)030102030405, "www.kfet.fr", pids[0]);
        long kid = dao.upsert(kfet);
        dao.addKfetProduct(new KfetProductAssociation(kid, pids[0]));
    }


    public void feed () {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long [] pids = feedProducts();
                feedKfet (pids);
            }
        });
    }

}
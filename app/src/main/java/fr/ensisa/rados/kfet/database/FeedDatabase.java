package fr.ensisa.rados.kfet.database;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.kfet.model.Product;
import fr.ensisa.rados.kfet.model.ProductType;

public class FeedDatabase {

    private void feedPersons() {
        ProductDao dao = AppDatabase.get().getProductDao();
        dao.upsert(new Product(1,"Pizza Salami", 3, ProductType.food, "Délicieuse Pizza", new Date(2022, 02, 01), 2));
        dao.upsert(new Product(2,"Pizza Chevre", 3, ProductType.food, "Délicieuse Pizza", new Date(2022, 02, 01), 2));
        dao.upsert(new Product(3,"Café", 3, ProductType.drinks, "Café crème", new Date(2023, 05, 01), 50));
    }

    public void feed () {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                feedPersons ();
            }
        });
    }

}
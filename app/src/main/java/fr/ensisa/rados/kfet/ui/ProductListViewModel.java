package fr.ensisa.rados.kfet.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.kfet.database.ProductDao;
import fr.ensisa.rados.kfet.model.Product;

public class ProductListViewModel extends ViewModel {
    public class PersonListViewModel extends ViewModel {

        private ProductDao productDao;
        private MediatorLiveData<List<Product>> products;

        public void setProductDao(ProductDao productDao) {
            this.productDao = productDao;
            this.products = new MediatorLiveData<>();
            this.products.addSource(productDao.getAll(), products::setValue);
        }

        public LiveData<List<Product>> getProducts() {
            return products;
        }

        public void deletePerson(Product product) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    productDao.delete(product);
                }
            });
        }

    }
}
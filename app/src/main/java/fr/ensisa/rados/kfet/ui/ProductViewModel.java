package fr.ensisa.rados.kfet.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.kfet.database.ProductDao;
import fr.ensisa.rados.kfet.R;
import fr.ensisa.rados.kfet.model.Product;
import fr.ensisa.rados.kfet.model.ProductType;

public class ProductViewModel  extends ViewModel
{
    static private final String TAG = "ProductViewModel";
    private ProductDao productDao;
    private MutableLiveData<Long> id = new MutableLiveData<>();
    private LiveData<Product> product;
    private MediatorLiveData<Boolean> productValidator;
    private MutableLiveData<String> name;
    private MutableLiveData<Integer> price;
    private MutableLiveData<ProductType>productType;
    private MutableLiveData<String>description;
    private MutableLiveData<Date> expirationDate;
    private MutableLiveData<Integer> quantity;
    private MutableLiveData<String> picture;

    private LiveData<Integer> nameValidator;
    private LiveData<Integer> priceValidator;
    private LiveData<Integer> productTypeValidator;
    private LiveData<Integer> descriptionValidator;
    private LiveData<Integer> expirationDateValidator;
    private LiveData<Integer> quantityValidator;
    private LiveData<Integer> pictureValidator;

    public void setProductDao(ProductDao productDao)
    {
        this.product = Transformations.switchMap(id, p -> productDao.getById(p));
        this.name = (MutableLiveData) Transformations.map(product, p->p.getName());
        this.productType = (MutableLiveData) Transformations.map(product, p->p.getProductType());
        this.description = (MutableLiveData) Transformations.map(product, p->p.getDescription());
        this.expirationDate = (MutableLiveData) Transformations.map(product, p->p.getExpirationDate());
        this.quantity = (MutableLiveData) Transformations.map(product, p->p.getQuantity());
        this.picture = (MutableLiveData) Transformations.map(product, p -> p.getPicture());
        this.nameValidator = Transformations.map(name, v->validateName(v));
        this.productTypeValidator = Transformations.map(productType, v->validateProductType(v));
        this.descriptionValidator =  Transformations.map(description, v->validateDescription(v));
        this.expirationDateValidator = Transformations.map(expirationDate, v->validateExpirationDate(v));
        this.quantityValidator =  Transformations.map(quantity, v->validateQuantity(v));
        productValidator = new MediatorLiveData<Boolean>();

        productValidator.setValue(Boolean.FALSE);
        productValidator.addSource(nameValidator, productValidatorObserver);
        productValidator.addSource(productTypeValidator, productValidatorObserver);
        productValidator.addSource(descriptionValidator, productValidatorObserver);
        productValidator.addSource(expirationDateValidator, productValidatorObserver);
        productValidator.addSource(quantityValidator, productValidatorObserver);



    }

    private Observer<Integer> productValidatorObserver = new Observer<Integer>() {
        @Override
        public void onChanged(Integer o) {
            boolean valid = true;
            if (valid && nameValidator.getValue() != null && nameValidator.getValue() > 0) valid = false;
            if (valid && priceValidator.getValue() != null && priceValidator.getValue()> 0) valid = false;
            if (valid && productTypeValidator.getValue() != null && productTypeValidator.getValue()> 0) valid = false;
            if (valid && expirationDateValidator.getValue() != null && expirationDateValidator.getValue()> 0) valid = false;
            if (valid && quantityValidator.getValue() != null && quantityValidator.getValue()> 0) valid = false;
            if (valid && descriptionValidator.getValue() != null && descriptionValidator.getValue()> 0) valid = false;
            productValidator.postValue(valid);
        }
    };

    private int validateName(String value) {
        if (value == null) return R.string.field_not_null;
        if (value.isEmpty()) return R.string.field_not_empty;
        return 0;
    }

    private int validatePrice(int value) {
        if (value <= 0) return R.string.price_must_be_positive;
        return 0;
    }

    private int validateProductType(ProductType value) {
        if (value == null) return R.string.field_not_null;
        switch (value) {
            case food:return 0;
            case drinks: return 0;


        }
        return R.string.productType_not_valid;
    }

    private int validatePicture(String value) {
        return 0;
    }

    private int validateDescription(String value) {
        if (value == null) return R.string.field_not_null;
        if (value.isEmpty()) return R.string.field_not_empty;
        return 0;
    }

    private int validateExpirationDate(Date value){return 0;}

    private int validateQuantity(int value){
        if (value < 0) return R.string.quantity_cant_be_negative;
        return 0;
    }


    public ProductDao getProductDao() {
        return productDao;
    }

    public MutableLiveData<Long> getId() {
        return id;
    }

    public void setId(MutableLiveData<Long> id) {
        this.id = id;
    }

    public LiveData<Product> getProduct() {
        return product;
    }

    public void setProduct(LiveData<Product> product) {
        this.product = product;
    }

    public MediatorLiveData<Boolean> getProductValidator() {
        return productValidator;
    }

    public void setProductValidator(MediatorLiveData<Boolean> productValidator) {
        this.productValidator = productValidator;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(MutableLiveData<String> name) {
        this.name = name;
    }

    public MutableLiveData<Integer> getPrice() {
        return price;
    }

    public void setPrice(MutableLiveData<Integer> price) {
        this.price = price;
    }

    public MutableLiveData<ProductType> getProductType() {
        return productType;
    }

    public void setProductType(MutableLiveData<ProductType> productType) {
        this.productType = productType;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public void setDescription(MutableLiveData<String> description) {
        this.description = description;
    }

    public MutableLiveData<Date> getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(MutableLiveData<Date> expirationDate) {
        this.expirationDate = expirationDate;
    }

    public MutableLiveData<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(MutableLiveData<Integer> quantity) {
        this.quantity = quantity;
    }

    public LiveData<Integer> getNameValidator() {
        return nameValidator;
    }

    public void setNameValidator(LiveData<Integer> nameValidator) {
        this.nameValidator = nameValidator;
    }

    public LiveData<Integer> getPriceValidator() {
        return priceValidator;
    }

    public void setPriceValidator(LiveData<Integer> priceValidator) {
        this.priceValidator = priceValidator;
    }

    public LiveData<Integer> getProductTypeValidator() {
        return productTypeValidator;
    }

    public void setProductTypeValidator(LiveData<Integer> productTypeValidator) {
        this.productTypeValidator = productTypeValidator;
    }

    public LiveData<Integer> getDescriptionValidator() {
        return descriptionValidator;
    }

    public void setDescriptionValidator(LiveData<Integer> descriptionValidator) {
        this.descriptionValidator = descriptionValidator;
    }

    public LiveData<Integer> getExpirationDateValidator() {
        return expirationDateValidator;
    }

    public void setExpirationDateValidator(LiveData<Integer> expirationDateValidator) {
        this.expirationDateValidator = expirationDateValidator;
    }

    public LiveData<Integer> getQuantityValidator() {
        return quantityValidator;
    }

    public void setQuantityValidator(LiveData<Integer> quantityValidator) {
        this.quantityValidator = quantityValidator;
    }

    public Observer<Integer> getProductValidatorObserver() {
        return productValidatorObserver;
    }

    public void setProductValidatorObserver(Observer<Integer> productValidatorObserver) {
        this.productValidatorObserver = productValidatorObserver;
    }
    public void save(LiveData<Product> product) {
        Product product = new Product(
                getProduct().getValue().getPid(),
                name.getValue(),
                price.getValue(),
                productType.getValue(),
                description.getValue(),
                expirationDate.getValue(),
                quantity.getValue()) ;
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productDao.upsert(product);
            }
        });
    }

    public void setId(long id) {
        this.id.postValue(id);
    }

    public void createProduct() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Product p = new Product();
                long id = productDao.upsert(p);
                setId(id);
            }
        });

    }
    public MutableLiveData<String> getPicture() {
        return picture;
    }
    public void setPicture(String absoluteFile) {
        getPicture().postValue(absoluteFile);
    }
}


package fr.ensisa.rados.kfet.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import fr.ensisa.rados.kfet.Picture;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long pid;
    private String name;
    private int price;
    private ProductType productType;
    private String description;
    private Date expirationDate;
    private int quantity;
    private String picture;
    public Product(){
        this.pid = 0;
    }

    @Ignore
    public Product(long pid, String name, int price, ProductType productType, String description, Date expirationDate, int quantity) {
        this();
        this.pid=pid;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.description = description;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture)
    {
        this.picture=picture;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", price=" + price +
                "$, productType=" + productType +
                ", description='" + description + '\'' +
                ", expirationDate=" + expirationDate +
                ", quantity=" + quantity +
                '}';
    }
}


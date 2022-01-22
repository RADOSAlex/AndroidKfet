package fr.ensisa.rados.kfet.model;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "kfets")
public class Kfet {
    @PrimaryKey(autoGenerate = true)
    private long kid;
    private String name;
    private String description;
    private Product[] products;
    private int phoneNumber;
    private String url;
    private String highRes;
    private Bitmap lowRes;

    public Kfet() {
        this.kid=0;
    }
    @Ignore
    public Kfet(long kid, String name, String description, Product[] products, int phoneNumber, String url, String highRes, Bitmap lowRes) {
        this.kid = kid;
        this.name = name;
        this.description = description;
        this.products = products;
        this.phoneNumber = phoneNumber;
        this.url = url;
        this.highRes = highRes;
        this.lowRes = lowRes;
    }

    public String getHighRes() {
        return highRes;
    }

    public void setHighRes(String highRes) {
        this.highRes = highRes;
    }

    public Bitmap getLowRes() {
        return lowRes;
    }

    public void setLowRes(Bitmap lowRes) {
        this.lowRes = lowRes;
    }

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


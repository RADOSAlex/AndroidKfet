package fr.ensisa.rados.kfet.model;

import java.util.Date;

public class Order {

    private long oid;
    private String name;
    private Date orderDate;
    private Date deliveryDate;
    private int price;
    private Product[] products;

    public Order(long oid) {
        this.oid = oid;
    }

    public Order(long oid, String name, Date orderDate, Date deliveryDate, int price, Product[] products) {
        this.oid = oid;
        this.name = name;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.price = price;
        this.products = products;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}

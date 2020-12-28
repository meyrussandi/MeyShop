package com.com.meyshop.model;

public class MyOrders {

    String nama_product;
    String url_thumbnail;
    String order_number;
    int total_harga, jumlah_product;

    //jika menggunakan constructor ini, nama variabel harus sama dengan nama item didatabase
    public  MyOrders(){

    }

    public MyOrders(String nama_Product, String url_thumbnail, String order_number,int total_harga, int jumlah_product) {
        this.nama_product = nama_Product;
        this.order_number = order_number;
        this.url_thumbnail = url_thumbnail;
        this.total_harga = total_harga;
        this.jumlah_product = jumlah_product;
    }

    public String getnama_Product() {
        return nama_product;
    }

    public void setnama_Product(String nama_Product) {
        this.nama_product = nama_Product;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public int gettotal_harga() {
        return total_harga;
    }

    public void settotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public int getjumlah_product() {
        return jumlah_product;
    }

    public void setjumlah_product(int jumlah_product) {
        this.jumlah_product = jumlah_product;
    }

}

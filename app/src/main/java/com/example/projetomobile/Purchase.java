package com.example.projetomobile;

class Purchase {

    private int purchase_id, user_id;
    private double total_price;
    private String date;

    public Purchase(int purchase_id, double total_price, String date, int user_id){
        this.purchase_id=purchase_id;
        this.total_price=total_price;
        this.date=date;
        this.user_id=user_id;
    }

    public Purchase(){

    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

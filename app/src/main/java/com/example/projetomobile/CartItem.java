package com.example.projetomobile;

class CartItem {
    private int id,product_id,quantity,created_by;

    public CartItem(int id, int product_id, int quantity, int created_by) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.created_by = created_by;
    }

    public CartItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
}

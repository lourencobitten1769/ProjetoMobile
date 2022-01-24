package com.example.projetomobile;

import java.io.Serializable;

class ProductPurchase  implements Serializable {

    private int productPurchase_id,product_id,purchase_id,quantity;

    public ProductPurchase(int productPurchase_id,int product_id, int purchase_id, int quantity){
        this.productPurchase_id=productPurchase_id;
        this.product_id=product_id;
        this.purchase_id=purchase_id;
        this.quantity=quantity;
    }

    public ProductPurchase(){

    }

    public int getProductPurchase_id() {
        return productPurchase_id;
    }

    public void setProductPurchase_id(int productPurchase_id) {
        this.productPurchase_id = productPurchase_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

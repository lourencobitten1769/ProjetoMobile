package com.example.projetomobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "lojadiferenciada";
    private static final int DB_VERSION = 1;

    private final SQLiteDatabase database;

    public DBHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
        this.database=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableUsers="CREATE TABLE users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "auth_key TEXT NOT NULL," +
                "password_hash TEXT NOT NULL," +
                "password_reset_token TEXT," +
                "email TEXT NOT NULL," +
                "morada TEXT NOT NULL," +
                "nif INTEGER NOT NULL," +
                "pontos INTEGER NOT NULL," +
                "socio BOOLEAN NOT NULL," +
                "status INTEGER NOT NULL," +
                "created_at INTEGER NOT NULL," +
                "updated_at INTEGER NOT NULL," +
                "verification_token TEXT NOT NULL);";

        String tableAuthAssignment="CREATE TABLE auth_assignment(" +
                "item_name TEXT NOT NULL PRIMARY KEY," +
                "user_id TEXT NOT NULL," +
                "created_at INTEGER," +
                "FOREIGN KEY(user_id) " +
                "REFERENCES users(user_id))";

        String tableAuthRule="CREATE TABLE auth_rule(" +
                "name TEXT NOT NULL PRIMARY KEY," +
                "data DATE," +
                "created_at INT,"+
                "updated_at INT);";

        String tableAuthItem="CREATE TABLE auth_item(" +
                "name TEXT NOT NULL PRIMARY KEY," +
                "type INTEGER NOT NULL," +
                "description TEXT," +
                "rule_name TEXT," +
                "data DATE," +
                "created_at INTEGER," +
                "updated_at INTEGER," +
                "FOREIGN KEY(rule_name)" +
                "REFERENCES auth_rule(name));";

        String tableAuthItemChild= "CREATE TABLE auth_item_child(" +
                "parent TEXT NOT NULL PRIMARY KEY," +
                "child TEXT NOT NULL," +
                "FOREIGN KEY(child)" +
                "REFERENCES auth_item(name));";

        String tableCategories="CREATE TABLE category(" +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category TEXT NOT NULL);";

        String tableOrders="CREATE TABLE orders(" +
                "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quantidade INTEGER NOT NULL);";

        String tableProducts= "CREATE TABLE products(" +
                "product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "size REAL," +
                "stock INTEGER NOT NULL," +
                "image TEXT NOT NULL," +
                "category_id INTEGER NOT NULL," +
                "FOREIGN KEY (category_id)" +
                "REFERENCES categories(category_id));";

        String tableProductsOrders="CREATE TABLE productsorders(" +
                "productOrder_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_id INTEGER NOT NULL," +
                "order_id INTEGER NOT NULL," +
                "FOREIGN KEY(product_id)" +
                "REFERENCES products(product_id)," +
                "FOREIGN KEY(order_id)" +
                "REFERENCES orders(order_id));";

        String tableProviders="CREATE TABLE providers(" +
                "provider_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "provider_name TEXT NOT NULL," +
                "nif INTEGER NOT NULL);";

        String tablePurchases="CREATE TABLE purchases(" +
                "purchase_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total_price REAL NOT NULL," +
                "date DATE NOT NULL," +
                "user_id INTEGER NOT NULL,"+
                "FOREIGN KEY(user_id)" +
                "REFERENCES users(user_id));";

        String tableProductsPurchases="CREATE TABLE productspurchases(" +
                "productpurchase_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_id INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL,"+
                "purchase_id INTEGER NOT NULL," +
                "FOREIGN KEY(product_id)" +
                "REFERENCES products(product_id)," +
                "FOREIGN KEY(purchase_id)" +
                "REFERENCES purchases(purchase_id));";

        String tableCartItems="CREATE TABLE cart_items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_id INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "created_by INTEGER NOT NULL,"+
                "FOREIGN KEY(product_id)"+
                "REFERENCES products(product_id),"+
                "FOREIGN KEY(created_by)"+
                "REFERENCES users(id))";

        db.execSQL(tableAuthAssignment);
        db.execSQL(tableAuthRule);
        db.execSQL(tableAuthItem);
        db.execSQL(tableAuthItemChild);
        db.execSQL(tableUsers);
        db.execSQL(tableCategories);
        db.execSQL(tableOrders);
        db.execSQL(tableProducts);
        db.execSQL(tableProductsOrders);
        db.execSQL(tableProviders);
        db.execSQL(tablePurchases);
        db.execSQL(tableProductsPurchases);
        db.execSQL(tableCartItems);


    }

    //-------------------------------//USER METHOD//------------------------------------------------
    public void adicionarUser(User user){
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("username",user.getUsername());
        values.put("auth_key", user.getAuth_key());
        values.put("password_hash", user.getPassword_hash());
        values.put("password_reset_token",user.getPassword_reset_token());
        values.put("email", user.getEmail());
        values.put("morada", user.getMorada());
        values.put("nif", user.getNif());
        values.put("pontos", user.getPontos());
        values.put("socio", user.isSocio());
        values.put("status", user.getStatus());
        values.put("created_at", user.getCreated_at());
        values.put("updated_at", user.getUpdated_At());
        values.put("verification_token", user.getVerification_token());

        this.database.insert("users", null, values);
    }

    public LinkedList<User> getAllUsers(){
        LinkedList<User> users = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM users",
                null);
        if(cursor.moveToFirst()){
            do{
                User user=new User();
                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setAuth_key(cursor.getString(2));
                user.setPassword_hash(cursor.getString(3));
                user.setPassword_reset_token(cursor.getString(4));
                user.setEmail(cursor.getString(5));
                user.setMorada(cursor.getString(6));
                user.setNif(cursor.getInt(7));
                user.setPontos(cursor.getInt(8));
                user.setSocio(false);
                user.setStatus(cursor.getInt(10));
                user.setCreated_at(cursor.getInt(11));
                user.setUpdated_At(cursor.getInt(12));
                user.setVerification_token(cursor.getString(13));
                users.add(user);
            }while(cursor.moveToNext());
        }
        return users;
    }

    public boolean guardarUser(User user){
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("username",user.getUsername());
        values.put("auth_key", user.getAuth_key());
        values.put("password_hash", user.getPassword_hash());
        values.put("password_reset_token",user.getPassword_reset_token());
        values.put("email", user.getEmail());
        values.put("morada", user.getMorada());
        values.put("nif", user.getNif());
        values.put("pontos", user.getPontos());
        values.put("socio", user.isSocio());
        values.put("status", user.getStatus());
        values.put("created_at", user.getCreated_at());
        values.put("updated_at", user.getUpdated_At());
        values.put("verification_token", user.getVerification_token());
        return this.database.update("users", values,
                "id = ?", new String[]{"" + user.getId()}) > 0;
    }

    public void removerUser(long idUser){
        this.database.delete("users", "id = ?",
                new String[]{"" + idUser});
    }

    public void removerAllUsers(){
        this.database.delete("users",null,null);
    }

    public User userlogado(String username){

        Cursor cursor = database.rawQuery("Select * FROM users Where username = ? ",new String[] {username});


        if (cursor.getCount()>0){
            User user= new User();
            if(cursor.moveToFirst()) {
                do {
                    user.setId(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setAuth_key(cursor.getString(2));
                    user.setPassword_hash(cursor.getString(3));
                    user.setPassword_reset_token(cursor.getString(4));
                    user.setEmail(cursor.getString(5));
                    user.setMorada(cursor.getString(6));
                    user.setNif(cursor.getInt(7));
                    user.setPontos(cursor.getInt(8));
                    user.setSocio(false);
                    user.setStatus(cursor.getInt(10));
                    user.setCreated_at(cursor.getInt(11));
                    user.setUpdated_At(cursor.getInt(12));
                    user.setVerification_token(cursor.getString(13));
                } while (cursor.moveToNext());
            }
            return user;
        }
        else {
            return null;
        }

    }

    //-------------------------------//END OF USER METHOD//-----------------------------------------

    //-------------------------------//PRODUCT METHOD//---------------------------------------------

    public void adicionarProduct(Product product){

        Log.d("size",String.valueOf(product.getSize()));
        ContentValues values = new ContentValues();
        values.put("product_id", product.getProduct_id());
        values.put("product_name",product.getProduct_name());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("size",product.getSize());
        values.put("stock", product.getStock());
        values.put("image", product.getImage());
        values.put("category_id", product.getCategory_id());

        this.database.insert("products", null, values);
    }
    public LinkedList<Product> getAllProducts(){
        LinkedList<Product> products = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM products",
                null);
        if(cursor.moveToFirst()){
            do{
                Product product=new Product();
                product.setProduct_id(cursor.getInt(0));
                product.setProduct_name(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(cursor.getInt(3));
                product.setSize(cursor.getInt(4));
                product.setStock(cursor.getInt(5));
                product.setImage(cursor.getString(6));
                product.setCategory_id(cursor.getInt(7));
                products.add(product);
            }while(cursor.moveToNext());
        }
        return products;
    }

    public Product getProductById(int productId){
        Cursor cursor=this.database.rawQuery("SELECT * FROM products WHERE product_id=" + productId,
                null);
        Product product=new Product();
        if(cursor.moveToFirst()){
            do{
                product.setProduct_id(cursor.getInt(0));
                product.setProduct_name(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(cursor.getInt(3));
                product.setSize(cursor.getInt(4));
                product.setStock(cursor.getInt(5));
                product.setImage(cursor.getString(6));
                product.setCategory_id(cursor.getInt(7));
            }while(cursor.moveToNext());
        }
        return product;

    }

    public LinkedList<Product> getProductsByCategory(int categoryId){
        LinkedList<Product> products = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM products WHERE category_id=" + categoryId,
                null);
        if(cursor.moveToFirst()){
            do{
                Product product=new Product();
                product.setProduct_id(cursor.getInt(0));
                product.setProduct_name(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(cursor.getInt(3));
                product.setSize(cursor.getInt(4));
                product.setStock(cursor.getInt(5));
                product.setImage(cursor.getString(6));
                product.setCategory_id(cursor.getInt(7));
                products.add(product);
            }while(cursor.moveToNext());
        }
        return products;
    }

    public boolean guardarProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("product_id", product.getProduct_id());
        values.put("product_name",product.getProduct_name());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("size",product.getSize());
        values.put("stock", product.getStock());
        values.put("image", product.getImage());
        values.put("category_id", product.getCategory_id());
        return this.database.update("products", values,
                "product_id = ?", new String[]{"" + product.getProduct_id()}) > 0;
    }

    public void removerProduct(long idProduct){
        this.database.delete("products", "product_id = ?",
                new String[]{"" + idProduct});
    }

    public void removerAllProducts(){
        this.database.delete("products",null,null);
    }

    //-------------------------------//END OF PRODUCT METHOD//--------------------------------------

    //-------------------------------//CATEGORY METHOD//--------------------------------------------

    public void adicionarCategory(Category category){

        ContentValues values = new ContentValues();
        values.put("category_id", category.getCategory_id());
        values.put("category",category.getCategory());

        this.database.insert("category", null, values);
    }

    public LinkedList<Category> getAllCategories(){
        LinkedList<Category> categories = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM category",
                null);
        if(cursor.moveToFirst()){
            do{
                Category category=new Category();
                category.setCategory_id(cursor.getInt(0));
                category.setCategory(cursor.getString(1));
                categories.add(category);
            }while(cursor.moveToNext());
        }
        return categories;
    }

    public void removerAllCategories(){
        this.database.delete("category",null,null);
    }

    //-------------------------------//END OF CATEGORY METHOD//-------------------------------------

    //-------------------------------//PURCHASE METHOD//-------------------------------------

    public LinkedList<Purchase> getPurchasesByUser(int userID) throws ParseException {
        LinkedList<Purchase> purchases = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM purchases where user_id=" + userID,
                null);
        if(cursor.moveToFirst()){
            do{
                Purchase purchase=new Purchase();
                purchase.setPurchase_id(cursor.getInt(0));
                purchase.setTotal_price(cursor.getDouble(1));
                purchase.setDate(cursor.getString(2));
                purchase.setUser_id(cursor.getInt(3));
                purchases.add(purchase);
            }while(cursor.moveToNext());
        }
        return purchases;
    }

    public void adicionarPurchase(Purchase purchase){

        ContentValues values = new ContentValues();
        values.put("purchase_id", purchase.getPurchase_id());
        values.put("total_price",purchase.getTotal_price());
        values.put("date", String.valueOf(purchase.getDate()));
        values.put("user_id",purchase.getUser_id());

        this.database.insert("purchases", null, values);
    }

    public void removerAllPurchases(){
        this.database.delete("purchases",null,null);
    }
    //-------------------------------//END OF PURCHASE METHOD//-------------------------------------
    //-------------------------------//CARTITEMS METHOD//-------------------------------------

    public void adicionarCartItem(CartItem cartItem){

        ContentValues values = new ContentValues();
        values.put("id", cartItem.getId());
        values.put("product_id",cartItem.getProduct_id());
        values.put("quantity", cartItem.getQuantity());
        values.put("created_by",cartItem.getCreated_by());

        this.database.insert("cart_items", null, values);
    }

    public LinkedList<CartItem> getCartByUser(int userID) throws ParseException {
        LinkedList<CartItem> cartitems = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM cart_items where created_by=" + userID,
                null);
        if(cursor.moveToFirst()){
            do{
                CartItem cartItem=new CartItem();
                cartItem.setId(cursor.getInt(0));
                cartItem.setProduct_id(cursor.getInt(1));
                cartItem.setQuantity(cursor.getInt(2));
                cartItem.setCreated_by(cursor.getInt(3));
                cartitems.add(cartItem);
            }while(cursor.moveToNext());
        }
        return cartitems;
    }

    public void removerAllCart(){
        this.database.delete("cart_items",null,null);
    }

    public void adicionarProductPurchase(ProductPurchase productPurchase){

            ContentValues values = new ContentValues();
            values.put("productpurchase_id", productPurchase.getProductPurchase_id());
            values.put("product_id",productPurchase.getProduct_id());
            values.put("purchase_id", productPurchase.getPurchase_id());
            values.put("quantity", productPurchase.getQuantity());

            this.database.insert("productspurchases", null, values);
    }

    public LinkedList<ProductPurchase> getallProductPurchases(){
        LinkedList<ProductPurchase> productPurchases = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM productspurchases",
                null);
        if(cursor.moveToFirst()){
            do{
                ProductPurchase productPurchase=new ProductPurchase();
                productPurchase.setProductPurchase_id(cursor.getInt(0));
                productPurchase.setProduct_id(cursor.getInt(1));
                productPurchase.setPurchase_id(cursor.getInt(2));
                productPurchase.setQuantity(cursor.getInt(3));
                productPurchases.add(productPurchase);
            }while(cursor.moveToNext());
        }
        return productPurchases;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

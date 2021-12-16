package com.example.projetomobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                "size REAL NOT NULL," +
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
                "quantity INTEGER NOT NULL," +
                "date DATE NOT NULL," +
                "user_id INTEGER NOT NULL,"+
                "FOREIGN KEY(user_id)" +
                "REFERENCES users(user_id));";

        String tableProductsPurchases="CREATE TABLE productspurchases(" +
                "productpurchase_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_id INTEGER NOT NULL," +
                "purchase_id INTEGER NOT NULL," +
                "FOREIGN KEY(product_id)" +
                "REFERENCES products(product_id)," +
                "FOREIGN KEY(purchase_id)" +
                "REFERENCES purchases(purchase_id));";

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


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

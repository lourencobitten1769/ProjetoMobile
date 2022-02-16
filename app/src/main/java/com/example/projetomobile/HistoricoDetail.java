package com.example.projetomobile;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoricoDetail extends AppCompatActivity {


    private DBHelper dbHelper;
    private RecyclerView rv_historicoDetail;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ProductPurchase> productPurchases=new ArrayList<>();
    AdapterHistoricoDetail adapterHistoricoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_detail);

        int id= getIntent().getIntExtra("id",0);

        dbHelper= new DBHelper(this);
        rv_historicoDetail=findViewById(R.id.rvHistoricoDetail);


        if(LoginActivity.isInternetConnection(this)){
            dbHelper.removerAllProductsPurchases();
        }


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url ="http://10.0.2.2/projetoweb/backend/web/index.php/api/productspurchases";



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("entrou","entrou");
                    for (int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        ProductPurchase productPurchase= new ProductPurchase();
                        productPurchase.setProductPurchase_id(jsonObject.getInt("productPurchase_id"));
                        productPurchase.setProduct_id(jsonObject.getInt("product_id"));
                        productPurchase.setPurchase_id(jsonObject.getInt("purchase_id"));
                        productPurchase.setQuantity(jsonObject.getInt("quantity"));

                        dbHelper.adicionarProductPurchase(productPurchase);


                    }

                    productPurchases.addAll(dbHelper.getProductPurchasesByPurchase(id));
                    Log.d("test","teste");
                    adapterHistoricoDetail = new AdapterHistoricoDetail(HistoricoDetail.this,productPurchases);
                    rv_historicoDetail.setAdapter(adapterHistoricoDetail);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("erro",error.toString());
            }
        });


        requestQueue.add(jsonArrayRequest);


        layoutManager=new LinearLayoutManager(this);
        rv_historicoDetail.setLayoutManager(layoutManager);


    }
}
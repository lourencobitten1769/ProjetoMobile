package com.example.projetomobile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    TextView txtProduto,txtDescricao;
    ImageView imageView;
    Spinner spinnerStock;
    Button btnAdicionarCarrinho;
    Button btnOk;
    Dialog dialog;

    int quantidade=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtProduto=findViewById(R.id.txtProduto);
        txtDescricao=findViewById(R.id.txtDescricao);
        imageView=findViewById(R.id.ivProduto);
        spinnerStock=findViewById(R.id.spinnerStock);
        btnAdicionarCarrinho=findViewById(R.id.btnAdicionar);
        dialog=new Dialog(this);

        Product product= (Product) getIntent().getSerializableExtra("product");
        txtProduto.setText(product.getProduct_name());
        txtDescricao.setText(product.getDescription());
        Glide.with(this).load("http://10.0.2.2/projetoweb/frontend/web/images/" + product.getImage()).into(imageView);

        List<Integer> list= new ArrayList<>();

        /*SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Integer userid=sharedPref.getInt(getString(R.string.userid),0);
        String username=sharedPref.getString(String.valueOf(R.string.username),"");
        String email=sharedPref.getString(String.valueOf(R.string.useremail),"");
        String morada=sharedPref.getString(String.valueOf(R.string.usermorada),"");*/

        User userLogado= (User) getIntent().getSerializableExtra("user");

        for (int i=1;i<=product.getStock();i++){
            list.add(i);
        }
        ArrayAdapter<Integer> adp1 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStock.setAdapter(adp1);



        spinnerStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantidade=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://10.0.2.2/projetoweb/backend/web/index.php/api/cart", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        openSucessDialog();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("product_id", String.valueOf(product.getProduct_id()));
                        map.put("quantity", String.valueOf(quantidade));
                        map.put("created_by", String.valueOf(userLogado.getId()));
                        return map;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });
    }

    private void openSucessDialog(){
        dialog.setContentView(R.layout.addtocart_checked_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk=dialog.findViewById(R.id.btnChecked_sucess);
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void openErrorDialog()
    {
        dialog.setContentView(R.layout.addtocart_error_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk=dialog.findViewById(R.id.btnChecked_sucess);
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }





}
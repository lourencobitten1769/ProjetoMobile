package com.example.projetomobile;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrinhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrinhoFragment extends Fragment {

    private RecyclerView mRecyclerCarrinho;
    private AdapterCarrinho adapterCarrinho;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<CartItem> itemCarrinhos=new ArrayList<>();
    TextView txtTotal;
    Button btnCheckout;
    DBHelper dbHelper;
    int total=0;
    int ultimoid=0;
    Dialog dialog;
    Button btnOk;
    ImageView sucess;
    GifImageView gifLoading;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarrinhoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarrinhoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarrinhoFragment newInstance(String param1, String param2) {
        CarrinhoFragment fragment = new CarrinhoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_carrinho, container, false);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Integer userid=sharedPref.getInt(getString(R.string.userid),0);
        String username=sharedPref.getString(String.valueOf(R.string.username),"");
        String email=sharedPref.getString(String.valueOf(R.string.useremail),"");
        String morada=sharedPref.getString(String.valueOf(R.string.usermorada),"");

        String url = "http://10.0.2.2/projetoweb/backend/web/index.php/api/cart";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        dbHelper=new DBHelper(getActivity());

        txtTotal=v.findViewById(R.id.txtHPreco);
        btnCheckout=v.findViewById(R.id.btnCheckout);
        dialog=new Dialog(getContext());
        sucess=v.findViewById(R.id.imgSucess);
        gifLoading=v.findViewById(R.id.gifLoading);

        if(LoginActivity.isInternetConnection(getActivity())){
            dbHelper.removerAllCart();
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        CartItem cartItem = new CartItem();
                        cartItem.setId(jsonObject.getInt("id"));
                        cartItem.setProduct_id(jsonObject.getInt("product_id"));
                        cartItem.setQuantity(jsonObject.getInt("quantity"));
                        cartItem.setCreated_by(jsonObject.getInt("created_by"));
                        dbHelper.adicionarCartItem(cartItem);

                    }
                    try {
                        itemCarrinhos.addAll(dbHelper.getCartByUser(userid));
                        mRecyclerCarrinho=v.findViewById(R.id.rvCarrinho);
                        mRecyclerCarrinho.setHasFixedSize(true);
                        layoutManager= new LinearLayoutManager(getContext());
                        adapterCarrinho=new AdapterCarrinho(itemCarrinhos,getContext());
                        mRecyclerCarrinho.scheduleLayoutAnimation();
                        mRecyclerCarrinho.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
                        mRecyclerCarrinho.setLayoutManager(layoutManager);
                        mRecyclerCarrinho.setAdapter(adapterCarrinho);

                        for (int i=0;i< itemCarrinhos.size();i++){
                            Product product=dbHelper.getProductById(itemCarrinhos.get(i).getProduct_id());
                            total=total + (product.getPrice()* itemCarrinhos.get(i).getQuantity());
                        }

                        txtTotal.setText("Total: " + total + "€");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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




        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/projetoweb/backend/web/index.php/api/purchase", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("purchase","Purchase add Successfully");

                            openSucessDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        openErrorDialog();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map= new HashMap<>();
                        map.put("total_price", String.valueOf(total));
                        Date date=new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        System.out.println(formatter.format(date));
                        map.put("date",formatter.format(date));
                        map.put("user_id", String.valueOf(userid));
                        return map;
                    }
                };

                RequestQueue requestQueueAddPurchase= Volley.newRequestQueue(getContext());
                requestQueueAddPurchase.add(stringRequest);

                JsonObjectRequest jsonObjectRequestLastPurchase= new JsonObjectRequest(Request.Method.GET, "http://10.0.2.2/projetoweb/backend/web/index.php/api/purchase/lastpurchase", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("ultimoid", String.valueOf(response.getInt("purchase_id")));
                            ultimoid=response.getInt("purchase_id") + 1;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueueLastPurchase= Volley.newRequestQueue(getContext());
                requestQueueLastPurchase.add(jsonObjectRequestLastPurchase);


                for (int k=0;k< itemCarrinhos.size();k++){
                    int finalK = k;
                    StringRequest stringRequestProductsPurchases= new StringRequest(Request.Method.POST, "http://10.0.2.2/projetoweb/backend/web/index.php/api/productspurchases", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d("productPurchase","ProductPurchase added successfully");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.d("erro",error.toString());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map= new HashMap<>();
                            map.put("product_id", String.valueOf(itemCarrinhos.get(finalK).getProduct_id()));
                            map.put("purchase_id", String.valueOf(ultimoid));
                            map.put("quantity",String.valueOf(itemCarrinhos.get(finalK).getQuantity()));
                            return map;
                        }
                    };

                    RequestQueue requestQueueAddProductsPurchases=Volley.newRequestQueue(getContext());
                    requestQueueAddProductsPurchases.add(stringRequestProductsPurchases);

                }

            }
        });


        /*adapterCarrinho.setOnItemClickListener(new AdapterCarrinho.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

                CarrinhoFragment.this.onDeleteClick(position);
            }
        });
*/

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                //onDeleteClick(viewHolder.getAdapterPosition());
                itemCarrinhos.remove(viewHolder.getAdapterPosition());
                adapterCarrinho.notifyDataSetChanged();

            }
        };

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerCarrinho);


        return v;
    }


    public void openSucessDialog(){


        dialog.setContentView(R.layout.purchasesucess_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk=dialog.findViewById(R.id.btnChecked_sucess);
        dialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gifLoading.setVisibility(View.INVISIBLE);
                sucess.setVisibility(View.VISIBLE);            }
        }, 5000);




        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void openErrorDialog(){

        dialog.setContentView(R.layout.purchasseerror_layout);
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

    public void onDeleteClick(int position){

        itemCarrinhos.remove(position);
        adapterCarrinho.notifyItemRemoved(position);
    }


}
package com.example.projetomobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicialFragment extends Fragment implements AdapterTypeHoriz.OnItemClickListener,AdapterProdutos.OnItemClickListener{


    private AdapterTypeHoriz adapterTypeHoriz;
    private AdapterProdutos adapterProdutos;
    private RecyclerView.LayoutManager layoutManager;
    List<Product> products=new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView horizontal_recycler;
    public LinkedList<Category> itemHorizontals;

    public RecyclerView rv_produtos;

    DBHelper dbHelper;




    public InicialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicialFragment newInstance(String param1, String param2) {
        InicialFragment fragment = new InicialFragment();
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
        View view = inflater.inflate(R.layout.fragment_inicial, container, false);

        horizontal_recycler=view.findViewById(R.id.horizontal_recycler);




        dbHelper= new DBHelper(getActivity());
        //RecyclerViewProdutos
        rv_produtos=view.findViewById(R.id.rv_produtos);
        products= new ArrayList<>();
        itemHorizontals=new LinkedList<>();


        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url ="http://10.0.2.2/projetoweb/backend/web/index.php/api/product";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("entrou","entrou");
                    for (int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        Product product= new Product();
                        product.setProduct_id(jsonObject.getInt("product_id"));
                        product.setPrice(jsonObject.getInt("price"));
                        if(jsonObject.isNull("size")){

                        }
                        else {
                            product.setStock(jsonObject.getInt("size"));
                        }
                        product.setStock(jsonObject.getInt("stock"));
                        product.setCategory_id(jsonObject.getInt("category_id"));
                        product.setProduct_name(jsonObject.getString("product_name"));
                        product.setDescription(jsonObject.getString("description"));
                        product.setImage(jsonObject.getString("image"));

                        dbHelper.adicionarProduct(product);

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





        String urlCategory="http://10.0.2.2/projetoweb/backend/web/index.php/api/category";

        JsonArrayRequest jsonArrayRequestCategory=new JsonArrayRequest(Request.Method.GET, urlCategory, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    try {
                        Log.d("entrou","entrou");
                        for (int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        Category category=new Category();
                        category.setCategory_id(jsonObject.getInt("category_id"));
                        category.setCategory(jsonObject.getString("category"));

                        dbHelper.adicionarCategory(category);

                        }


                    }catch (Exception e) {
                        e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.d("erro", String.valueOf(error));
            }
        });
        RequestQueue requestQueueCategory=new Volley().newRequestQueue(getActivity());
        requestQueueCategory.add(jsonArrayRequestCategory);


        products.addAll(dbHelper.getAllProducts());
        horizontal_recycler.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterProdutos= new AdapterProdutos(getContext(),products,this);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        rv_produtos.setLayoutManager(gridLayoutManager);
        rv_produtos.setAdapter(adapterProdutos);


        itemHorizontals.addAll(dbHelper.getAllCategories());
        adapterTypeHoriz=new AdapterTypeHoriz(itemHorizontals,getActivity(),this);

        horizontal_recycler.setAdapter(adapterTypeHoriz);


        return view;
    }


    @Override
    public void onItemCLick(int position) {
        LinkedList<Product> products=dbHelper.getProductsByCategory(itemHorizontals.get(position).getCategory_id());
        AdapterProdutos adapterProdutos=new AdapterProdutos(getContext(),products,this);
        rv_produtos.setAdapter(adapterProdutos);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        rv_produtos.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void OnItemClick(int position) {
        User userLogado=(User) this.getArguments().getSerializable("user");
        Product productSelecionado= products.get(position);
        Bundle bundleProduct= new Bundle();
        bundleProduct.putSerializable("product",productSelecionado);
        bundleProduct.putSerializable("user",userLogado);
        Intent intent=new Intent(getContext(),DetailActivity.class);
        intent.putExtras(bundleProduct);
        startActivity(intent);
    }
}






















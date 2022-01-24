package com.example.projetomobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private RecyclerView mRecyclerHistorico;
    private AdapterHistorico adapterHistorico;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<Purchase> purchases=new ArrayList<>();
    EditText txtInputUser, txtInputEmail, txtInputMorada;
    Button btnEditarPerfil;
    DBHelper dbHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        View v= inflater.inflate(R.layout.fragment_perfil,container,false);

        txtInputUser=v.findViewById(R.id.edtUser);
        txtInputEmail=v.findViewById(R.id.edtEmail);
        txtInputMorada=v.findViewById(R.id.edtMorada);
        btnEditarPerfil=v.findViewById(R.id.btnEditarPerfil);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Integer userid=sharedPref.getInt(getString(R.string.userid),0);
        String username=sharedPref.getString(String.valueOf(R.string.username),"");
        String email=sharedPref.getString(String.valueOf(R.string.useremail),"");
        String morada=sharedPref.getString(String.valueOf(R.string.usermorada),"");


        Log.d("teste",userid.toString());
        Log.d("teste",username);
        Log.d("teste",email);
        Log.d("teste",morada);


        txtInputUser.setText(username);
        txtInputUser.requestFocus();
        txtInputEmail.setText(email);
        txtInputEmail.requestFocus();
        txtInputMorada.setText(morada);
        txtInputMorada.requestFocus();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url= "http://10.0.2.2/projetoweb/backend/web/index.php/api/purchase";
        dbHelper=new DBHelper(getActivity());

        if(LoginActivity.isInternetConnection(getActivity())){
            dbHelper.removerAllPurchases();
        }

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        Purchase purchase=new Purchase();
                        purchase.setPurchase_id(jsonObject.getInt("purchase_id"));
                        purchase.setTotal_price(jsonObject.getDouble("total_price"));
                        purchase.setDate(jsonObject.getString("date"));
                        purchase.setUser_id(jsonObject.getInt("user_id"));

                        dbHelper.adicionarPurchase(purchase);
                    }
                    try {
                        purchases.addAll(dbHelper.getPurchasesByUser(userid));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    mRecyclerHistorico=v.findViewById(R.id.rvHistorico);
                    mRecyclerHistorico.setHasFixedSize(true);
                    layoutManager=new LinearLayoutManager(getContext());
                    adapterHistorico= new AdapterHistorico(purchases);
                    mRecyclerHistorico.scheduleLayoutAnimation();
                    mRecyclerHistorico.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

                    mRecyclerHistorico.setLayoutManager(layoutManager);
                    mRecyclerHistorico.setAdapter(adapterHistorico);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlPut= "http://10.0.2.2/projetoweb/backend/web/index.php/api/user/" + userid;
                RequestQueue requestQueuePost=Volley.newRequestQueue(getActivity());
                StringRequest stringRequest= new StringRequest(Request.Method.PUT, urlPut, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response","Positive Response");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response","Negative Response");
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params= new HashMap<String,String>();
                        params.put("username", String.valueOf(txtInputUser.getText()));
                        params.put("email",String.valueOf(txtInputEmail.getText()));
                        params.put("morada",String.valueOf(txtInputMorada.getText()));
                        return params;
                    }
                };
                requestQueuePost.add(stringRequest);
            }
        });




        return v;
    }
}
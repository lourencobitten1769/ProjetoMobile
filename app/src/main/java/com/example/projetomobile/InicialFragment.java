package com.example.projetomobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicialFragment extends Fragment {


    private AdapterTypeHoriz adapterTypeHoriz;
    private AdapterProdutos adapterProdutos;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Product> products=new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView horizontal_recycler;
    public ArrayList<ItemHorizontal> itemHorizontals;

    private RecyclerView rv_produtos;

    List<String> titles;
    List<Integer> images;




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
        horizontal_recycler.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false));

        //RecyclerViewProdutos
        rv_produtos=view.findViewById(R.id.rv_produtos);
        titles= new ArrayList<>();
        images= new ArrayList<>();

        titles.add("FirstItem");
        titles.add("SecondItem");
        titles.add("ThirdItem");
        titles.add("FourthItem");
        titles.add("FirstItem");
        titles.add("SecondItem");
        titles.add("ThirdItem");
        titles.add("FourthItem");

        images.add(R.drawable.ic_baseline_home_24);
        images.add(R.drawable.ic_baseline_home_24);
        images.add(R.drawable.ic_baseline_home_24);
        images.add(R.drawable.ic_baseline_home_24);
        images.add(R.drawable.ic_baseline_home_24);
        images.add(R.drawable.ic_baseline_home_24);
        images.add(R.drawable.ic_baseline_home_24);
        images.add(R.drawable.ic_baseline_home_24);

        adapterProdutos= new AdapterProdutos(getContext(),titles,images);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        rv_produtos.setLayoutManager(gridLayoutManager);
        rv_produtos.setAdapter(adapterProdutos);


        String[] itemType = {"Carros", "Roupas", "Animais", "Bebé", "Informática"};

        itemHorizontals=new ArrayList<>();


        for (int i=0;i<itemType.length; i++){
            ItemHorizontal itemHorizontal = new ItemHorizontal(itemType[i]);
            itemHorizontals.add(itemHorizontal);
        }

        horizontal_recycler.setAdapter(new AdapterTypeHoriz(itemHorizontals));

        return view;
    }
}






















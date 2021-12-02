package com.example.projetomobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicialFragment extends Fragment {


    private AdapterTypeHoriz adapterTypeHoriz;
    private RecyclerView.LayoutManager layoutManager;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView horizontal_recycler;
    public ArrayList<ItemHorizontal> itemHorizontals;



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

        //Create String Array
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






















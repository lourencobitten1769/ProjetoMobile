package com.example.projetomobile;

import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private RecyclerView mRecyclerHistorico;
    private AdapterHistorico adapterHistorico;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<ItemHistorico> itemHistoricos=new ArrayList<>();

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

        itemHistoricos.add(new ItemHistorico(R.drawable.house,"11/12/2021","60€"));
        itemHistoricos.add(new ItemHistorico(R.drawable.house,"02/06/2005","40€"));
        itemHistoricos.add(new ItemHistorico(R.drawable.house,"11/07/2018","22€"));

        mRecyclerHistorico=v.findViewById(R.id.rvHistorico);
        mRecyclerHistorico.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        adapterHistorico= new AdapterHistorico(itemHistoricos);
        mRecyclerHistorico.scheduleLayoutAnimation();
        mRecyclerHistorico.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));

        mRecyclerHistorico.setLayoutManager(layoutManager);
        mRecyclerHistorico.setAdapter(adapterHistorico);


        return v;
    }
}
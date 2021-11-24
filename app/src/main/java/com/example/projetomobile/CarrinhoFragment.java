package com.example.projetomobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrinhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrinhoFragment extends Fragment {

    private RecyclerView mRecyclerCarrinho;
    private AdapterCarrinho adapterCarrinho;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<ItemCarrinho> itemCarrinhos=new ArrayList<>();



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
        View v= inflater.inflate(R.layout.fragment_carrinho,container,false);


        itemCarrinhos.add(new ItemCarrinho(R.drawable.house,"Carro","40000€"));
        itemCarrinhos.add(new ItemCarrinho(R.drawable.house,"Carro","40000€"));
        itemCarrinhos.add(new ItemCarrinho(R.drawable.house,"Carro","40000€"));
        itemCarrinhos.add(new ItemCarrinho(R.drawable.house,"Carro","40000€"));
        itemCarrinhos.add(new ItemCarrinho(R.drawable.house,"Carro","40000€"));
        itemCarrinhos.add(new ItemCarrinho(R.drawable.house,"Carro","40000€"));

        mRecyclerCarrinho=v.findViewById(R.id.rvCarrinho);
        mRecyclerCarrinho.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(getContext());
        adapterCarrinho=new AdapterCarrinho(itemCarrinhos);
        mRecyclerCarrinho.scheduleLayoutAnimation();
        mRecyclerCarrinho.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));


        mRecyclerCarrinho.setLayoutManager(layoutManager);
        mRecyclerCarrinho.setAdapter(adapterCarrinho);


        adapterCarrinho.setOnItemClickListener(new AdapterCarrinho.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                changeItem(position,"Clicked");
            }

            @Override
            public void onDeleteClick(int position) {

                CarrinhoFragment.this.onDeleteClick(position);
            }
        });


        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                itemCarrinhos.remove(viewHolder.getAdapterPosition());
                adapterCarrinho.notifyDataSetChanged();



            }
        };

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerCarrinho);



        return v;
    }
    public void changeItem(int position,String text){
        itemCarrinhos.get(position).changeText("Clicked");
        adapterCarrinho.notifyItemChanged(position);
    }



    public void onDeleteClick(int position){

        itemCarrinhos.remove(position);
        adapterCarrinho.notifyItemRemoved(position);
    }
}
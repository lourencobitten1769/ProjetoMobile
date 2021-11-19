package com.example.projetomobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.CarrinhoViewHolder> {
    private final ArrayList<ItemCarrinho> mCarrinhoList;

    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImagem;
        public TextView mTitulo;
        public TextView mPreco;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);
            mImagem=itemView.findViewById(R.id.imageProduto);
            mTitulo=itemView.findViewById(R.id.txtNome);
            mPreco=itemView.findViewById(R.id.txtPreco);
        }
    }

    public AdapterCarrinho(ArrayList<ItemCarrinho> carrinhoList){

        mCarrinhoList= carrinhoList;

    }


    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho,parent,false);
        CarrinhoViewHolder carrinhoViewHolder= new CarrinhoViewHolder(v);
        return carrinhoViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {

        ItemCarrinho currentItem= mCarrinhoList.get(position);

        holder.mImagem.setImageResource(currentItem.getmImagem());
        holder.mTitulo.setText(currentItem.getmTitulo());
        holder.mPreco.setText(currentItem.getmPreco());

    }

    @Override
    public int getItemCount() {
        return mCarrinhoList.size();
    }


}

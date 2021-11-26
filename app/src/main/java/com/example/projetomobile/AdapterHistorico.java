package com.example.projetomobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.HistoricoViewHolder> {
    private final ArrayList<ItemHistorico> mHistoricoList;
    private AdapterCarrinho.OnItemClickListener mListener;


    public static class HistoricoViewHolder extends RecyclerView.ViewHolder{
        public ImageView mHImagem;
        public TextView mHTitulo;
        public TextView mHPreco;
        public HistoricoViewHolder(@NonNull View itemView, AdapterCarrinho.OnItemClickListener mListener) {
            super(itemView);
            mHImagem=itemView.findViewById(R.id.imageHProduto);
            mHTitulo=itemView.findViewById(R.id.txtHData);
            mHPreco=itemView.findViewById(R.id.txtHPreco);
        }
    }

    public AdapterHistorico(ArrayList<ItemHistorico> historicoList)
    {
        mHistoricoList=historicoList;
    }


    @NonNull
    @Override
    public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico,parent,false);
        HistoricoViewHolder historicoViewHolder= new HistoricoViewHolder(v, mListener);
        return historicoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoViewHolder holder, int position) {

        ItemHistorico currentItem=mHistoricoList.get(position);

        holder.mHImagem.setImageResource(currentItem.getmImagem());
        holder.mHTitulo.setText(currentItem.getmTitulo());
        holder.mHPreco.setText(currentItem.getmPreco());


    }


    @Override
    public int getItemCount() {
        return mHistoricoList.size();
    }
}

package com.example.projetomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.HistoricoViewHolder> {
    private final ArrayList<Purchase> mPurchases;
    private AdapterHistorico.OnItemClickListener mListener;
    private Context context;


    public static class HistoricoViewHolder extends RecyclerView.ViewHolder{
        public ImageView mHImagem;
        public TextView mHTitulo;
        public TextView mHPreco;
        public HistoricoViewHolder(@NonNull View itemView, AdapterHistorico.OnItemClickListener mListener) {
            super(itemView);
            mHImagem=itemView.findViewById(R.id.imageHProduto);
            mHTitulo=itemView.findViewById(R.id.txtHData);
            mHPreco=itemView.findViewById(R.id.txtHPreco);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        int position= getAdapterPosition();
                        mListener.OnItemClick(position);

                }
            });
        }
    }

    public AdapterHistorico(Context context, ArrayList<Purchase> mPurchases, AdapterHistorico.OnItemClickListener listener){
        this.context=context;
        this.mPurchases = mPurchases;
        this.mListener=listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
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

        Purchase currentItem=mPurchases.get(position);

        holder.mHTitulo.setText(String.valueOf(currentItem.getDate()));
        holder.mHPreco.setText(String.valueOf(currentItem.getTotal_price()) + "€");


    }


    @Override
    public int getItemCount() {
        return mPurchases.size();
    }
}

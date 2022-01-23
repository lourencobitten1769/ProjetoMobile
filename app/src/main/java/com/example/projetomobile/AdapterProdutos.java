package com.example.projetomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.ProdutosViewholder> {

    List<Product> products;
    Context context;
    LayoutInflater inflater;
    public OnItemClickListener mListener;



    public AdapterProdutos(Context context,List<Product> products,OnItemClickListener listener){
        this.context=context;
        this.products=products;
        this.inflater = LayoutInflater.from(context);
        mListener=listener;
    }

    public AdapterProdutos(Context context, List<Product> products) {
        this.context=context;
        this.products=products;
        this.inflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    @NonNull
    @Override
    public ProdutosViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_grid_layout,parent,false);
        ProdutosViewholder produtosViewholder=new ProdutosViewholder(view,mListener);
        return produtosViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosViewholder holder, int position) {
        holder.title.setText(products.get(position).getProduct_name());
        //holder.gridImage.setImageResource(products.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProdutosViewholder extends RecyclerView.ViewHolder{
        OnItemClickListener listener;
        TextView title;
        ImageView gridImage;

        public ProdutosViewholder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener=listener;
            title=itemView.findViewById(R.id.txtProduto);
            gridImage=itemView.findViewById(R.id.ivProduto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();
                    mListener.OnItemClick(position);
                }
            });


        }
    }

}

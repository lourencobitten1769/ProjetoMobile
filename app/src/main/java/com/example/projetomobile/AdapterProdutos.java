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

    public AdapterProdutos(Context context,List<Product> products){
        this.products=products;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProdutosViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new ProdutosViewholder(view);
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

    public static class ProdutosViewholder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView gridImage;

        public ProdutosViewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.txtProduto);
            gridImage=itemView.findViewById(R.id.ivProduto);
        }
    }

}

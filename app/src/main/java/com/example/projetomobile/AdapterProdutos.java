package com.example.projetomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.ProdutosViewholder> implements Filterable {

    List<Product> products;
    Context context;
    LayoutInflater inflater;
    public OnItemClickListener mListener;
    ArrayList<Product> fullList;



    public AdapterProdutos(Context context,List<Product> products,OnItemClickListener listener){
        this.context=context;
        this.products=products;
        this.inflater = LayoutInflater.from(context);
        mListener=listener;

        fullList=new ArrayList<>(products);
    }

    public AdapterProdutos(Context context, List<Product> products) {
        this.context=context;
        this.products=products;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public Filter getFilter() {
        return search_filter;
    }

    private Filter search_filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Product> filteredList = new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filteredList.addAll(fullList);
            }
            else {
                String filterPattern= constraint.toString().toLowerCase().trim();
                for(Product product : fullList){
                    if(product.getProduct_name().toUpperCase().contains(filterPattern)){
                        filteredList.add(product);
                    }
                }
            }
            FilterResults results= new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            products.clear();
            products.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

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
        holder.preco.setText(String.valueOf(products.get(position).getPrice()) + "€");
        //holder.gridImage.setImageResource(products.get(position).getImage());

        Glide.with(context).load("http://10.0.2.2/projetoweb/frontend/web/images/" + products.get(position).getImage()).into(holder.gridImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProdutosViewholder extends RecyclerView.ViewHolder{
        OnItemClickListener listener;
        TextView title,preco;
        ImageView gridImage;

        public ProdutosViewholder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener=listener;
            title=itemView.findViewById(R.id.txtProduto);
            preco=itemView.findViewById(R.id.txtPrecoProduto);
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

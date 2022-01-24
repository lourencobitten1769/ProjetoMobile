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

class AdapterHistoricoDetail extends RecyclerView.Adapter<AdapterHistoricoDetail.HistoricoDetailViewHolder>
{
    private final ArrayList<ProductPurchase> mPurchasesDetail;
    private AdapterHistoricoDetail.OnItemClickListener mListener;
    Context context;
    DBHelper dbHelper;


    public static class HistoricoDetailViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgProduto;
        public TextView txtNomeProduto;
        public TextView txtPreco;
        public TextView txtQuantidade;

        public HistoricoDetailViewHolder(View itemView,OnItemClickListener listener)
        {
            super(itemView);
            //imgProduto=itemView.findViewById(R.id.imgProduto);
            txtNomeProduto=itemView.findViewById(R.id.txtNomeProduto);
            txtPreco=itemView.findViewById(R.id.txtPreco);
            txtQuantidade=itemView.findViewById(R.id.txtQuantidade);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if( listener!= null){
                        int position= getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }

                }
            });

        }
    }

    public AdapterHistoricoDetail(Context context, ArrayList<ProductPurchase> mPurchasesDetail, OnItemClickListener listener){
        this.context=context;
        this.mPurchasesDetail = mPurchasesDetail;
        this.mListener=listener;
    }

    public AdapterHistoricoDetail(ArrayList<ProductPurchase> purchases, Context context)
    {
        mPurchasesDetail=purchases;
        dbHelper=new DBHelper(context);
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    @NonNull
    @Override
    public HistoricoDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_historico,parent,false);
        AdapterHistoricoDetail.HistoricoDetailViewHolder historicoDetailViewHolder= new AdapterHistoricoDetail.HistoricoDetailViewHolder(v,mListener);
        return historicoDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoDetailViewHolder holder, int position) {

        Product product= dbHelper.getProductById(mPurchasesDetail.get(position).getProduct_id());
        holder.txtNomeProduto.setText(product.getProduct_name());
        holder.txtPreco.setText("Preço:" + String.valueOf(product.getPrice()));
        holder.txtQuantidade.setText("Quantity:" + String.valueOf(mPurchasesDetail.get(position).getQuantity()));
    }


    @Override
    public int getItemCount() {
        return mPurchasesDetail.size();
    }


}

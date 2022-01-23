package com.example.projetomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.CarrinhoViewHolder> {
    private final ArrayList<CartItem> mCarrinhoList;
    private OnItemClickListener mListener;
    DBHelper dbHelper;



    public interface OnItemClickListener{
        void onItemCLick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        public TextView mProduto;
        public TextView mPreco;
        public TextView mQuantidade;


        public CarrinhoViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mProduto=itemView.findViewById(R.id.txtHData);
            mPreco=itemView.findViewById(R.id.txtHPreco);
            mQuantidade=itemView.findViewById(R.id.txtHQuantidade);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listener != null){
                        int position= getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listener.onItemCLick(position);
                        }
                    }

                }
            });
        }
    }

    public AdapterCarrinho(ArrayList<CartItem> carrinhoList, Context context){

        mCarrinhoList= carrinhoList;
        dbHelper=new DBHelper(context);

    }


    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho,parent,false);
        CarrinhoViewHolder carrinhoViewHolder= new CarrinhoViewHolder(v, mListener);
        return carrinhoViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {

        Product product=dbHelper.getProductById(mCarrinhoList.get(position).getProduct_id());
        holder.mProduto.setText(product.getProduct_name());
        holder.mPreco.setText(holder.mPreco.getText()+String.valueOf(product.getPrice()));
        holder.mQuantidade.setText(holder.mQuantidade.getText()+String.valueOf(mCarrinhoList.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return mCarrinhoList.size();
    }


}

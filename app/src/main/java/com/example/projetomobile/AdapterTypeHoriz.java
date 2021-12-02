package com.example.projetomobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTypeHoriz extends RecyclerView.Adapter<AdapterTypeHoriz.TypeViewHolder>{
    private final ArrayList<ItemHorizontal> mItemHorizontals;
    private OnItemClickListener mListener;




    public AdapterTypeHoriz(ArrayList<ItemHorizontal> itemHorizontals) {
        mItemHorizontals = itemHorizontals;
    }


    public interface OnItemClickListener{
        void onItemCLick(int position);

    }


    public void setOnItemClickListener(AdapterTypeHoriz.OnItemClickListener listener){
        mListener= listener;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type,parent,false);
        TypeViewHolder typeViewHolder = new TypeViewHolder(v, mListener);
        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {

        holder.txtType.setText(mItemHorizontals.get(position).getItemType());
    }


    @Override
    public int getItemCount() {
        return mItemHorizontals.size();
    }



    public class TypeViewHolder extends RecyclerView.ViewHolder {
        public TextView txtType;

        public TypeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtType=itemView.findViewById(R.id.txtType);


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
}

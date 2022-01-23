package com.example.projetomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class AdapterTypeHoriz extends RecyclerView.Adapter<AdapterTypeHoriz.TypeViewHolder>{
    private final LinkedList<Category> mItemHorizontals;
    private OnItemClickListener mListener;
    private Context context;
    DBHelper dbHelper;




    public AdapterTypeHoriz(LinkedList<Category> itemHorizontals,Context context,OnItemClickListener listener) {
        mItemHorizontals = itemHorizontals;
        this.context=context;
        dbHelper=new DBHelper(context);
        mListener=listener;
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

        holder.txtType.setText(mItemHorizontals.get(position).getCategory());
    }


    @Override
    public int getItemCount() {
        return mItemHorizontals.size();
    }



    public class TypeViewHolder extends RecyclerView.ViewHolder {
        OnItemClickListener listener;
        public TextView txtType;

        public TypeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener=listener;
            txtType=itemView.findViewById(R.id.txtType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= getAdapterPosition();
                    mListener.onItemCLick(position);
                }
            });

        }
    }
}

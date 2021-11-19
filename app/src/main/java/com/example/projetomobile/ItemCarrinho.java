package com.example.projetomobile;

import android.widget.ImageView;
import android.widget.TextView;

class ItemCarrinho {
    private int mImagem;
    private String mTitulo;
    private String mPreco;

    public ItemCarrinho(int imagem,String titulo,String preco){

        mImagem=imagem;
        mTitulo=titulo;
        mPreco=preco;

    }

    public int getmImagem(){
        return mImagem;
    }

    public String getmTitulo()
    {
        return mTitulo;
    }

    public String getmPreco(){
        return mPreco;
    }

}

package com.example.projetomobile;

class ItemHistorico {

    private int mHImagem;
    private String mHTitulo;
    private String mHPreco;

    public ItemHistorico(int imagem, String titulo, String preco)
    {
        mHImagem=imagem;
        mHTitulo=titulo;
        mHPreco=preco;

    }

    public int getmImagem(){
        return mHImagem;
    }

    public String getmTitulo()
    {
        return mHTitulo;
    }

    public String getmPreco(){
        return mHPreco;
    }


}

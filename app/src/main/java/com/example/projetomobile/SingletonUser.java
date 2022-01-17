package com.example.projetomobile;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class SingletonUser {
    private static DBHelper dbHelper;
    private static SingletonUser singletonUser;
    private static LinkedList<User> users;

    public static void iniciarBD(Context context){
        dbHelper=new DBHelper(context);
    }

    public void lerBD(){
        this.users=dbHelper.getAllUsers();
    }

    public void gravarBD(){
        dbHelper.removerAllUsers();
        for (User user:users){
            dbHelper.adicionarUser(user);
        }
    }
    public void adicionarUser(User user) {
        users.add(user);
        dbHelper.adicionarUser(user);
    }

    public void removerUser(User user){
        if(users.contains(user)) {
            users.remove(user);
            dbHelper.removerUser(user.getId());
        }
    }

    public static SingletonUser getInstance(Context context){
            singletonUser = new SingletonUser();
            iniciarBD(context);
            return singletonUser;
    }
}

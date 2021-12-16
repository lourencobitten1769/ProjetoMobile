package com.example.projetomobile;

public class User {
    private int id,nif,pontos,status,created_at,updated_At;
    private String username,auth_key,password_hash,password_reset_token,email,morada, verification_token;
    private boolean socio;

    public User(int id, int nif, int pontos, int status, int created_at, int updated_At, String username, String auth_key, String password_hash, String password_reset_token, String email, String morada, String verification_token, boolean socio) {
        this.id = id;
        this.nif = nif;
        this.pontos = pontos;
        this.status = status;
        this.created_at = created_at;
        this.updated_At = updated_At;
        this.username = username;
        this.auth_key = auth_key;
        this.password_hash = password_hash;
        this.password_reset_token = password_reset_token;
        this.email = email;
        this.morada = morada;
        this.verification_token = verification_token;
        this.socio = socio;
    }
}

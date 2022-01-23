package com.example.projetomobile;

import java.io.Serializable;

public class User implements Serializable {
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

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(int updated_At) {
        this.updated_At = updated_At;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getPassword_reset_token() {
        return password_reset_token;
    }

    public void setPassword_reset_token(String password_reset_token) {
        this.password_reset_token = password_reset_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getVerification_token() {
        return verification_token;
    }

    public void setVerification_token(String verification_token) {
        this.verification_token = verification_token;
    }

    public boolean isSocio() {
        return socio;
    }

    public void setSocio(boolean socio) {
        this.socio = socio;
    }
}


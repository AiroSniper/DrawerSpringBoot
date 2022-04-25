package com.example.market.beans;

import android.sax.StartElementListener;

import java.util.Date;

public class Machine {
    private int id;
    private String ref;
    private Double prix;
    private String dateAchat;
    private String maqrque;

    public Machine(int id, String ref, Double prix, String  dateAchat, String maqrque) {
        this.id = id;
        this.ref = ref;
        this.prix = prix;
        this.dateAchat = dateAchat;
        this.maqrque = maqrque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String  getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getMaqrque() {
        return maqrque;
    }

    public void setMaqrque(String maqrque) {
        this.maqrque = maqrque;
    }
}

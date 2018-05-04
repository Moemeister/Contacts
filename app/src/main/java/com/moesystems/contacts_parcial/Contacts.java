package com.moesystems.contacts_parcial;

import java.io.Serializable;

public class Contacts implements Serializable{
    private String name;
    private int img;
    private String phone;
    private boolean favorito;
    public static String KEY_CONTACT = "KEY_CONTACT";

    public Contacts() {
    }

    public Contacts(String name, int img) {
        this.name = name;
        this.img = img;
    }
    public Contacts(String name,String phone, int img) {
        this.name = name;
        this.img = img;
        this.phone = phone;
        favorito = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void set(boolean favorito ){
        this.favorito = favorito;
    }

    public boolean yesorno(){
        return favorito;
    }
}

package com.moesystems.contacts_parcial;

public class Contacts {
    private String name;
    private int img;

    public Contacts() {
    }

    public Contacts(String name, int img) {
        this.name = name;
        this.img = img;
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
}

package com.moesystems.contacts_parcial;

public class Contacts {
    private String name;
    private int img;
    private String phone;

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
}

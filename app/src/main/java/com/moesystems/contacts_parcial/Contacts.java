package com.moesystems.contacts_parcial;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class Contacts implements Parcelable{
    private String name;
    private int img;
    private Uri img2;
    private String phone;
    private boolean favorito;
    public static String KEY_CONTACT = "KEY_CONTACT";

    public Contacts() {
    }

    public Contacts(String name,String phone, int img) {
        this.name = name;
        this.img = img;
        this.phone = phone;
        favorito = false;
    }
    public Contacts(String name,String phone, Uri img2) {
        this.name = name;
        this.img2 = img2;
        this.phone = phone;
        favorito = false;
    }

    public Uri getImg2() {
        return img2;
    }

    public void setImg2(Uri img2) {
        this.img2 = img2;
    }

    protected Contacts(Parcel in) {
        //para leer debe estar en el mismo orden que los escribo
        name = in.readString();
        phone = in.readString();
        img = in.readInt();
        favorito = in.readByte() != 0;
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeInt(img);
        parcel.writeByte((byte) (favorito?1:0));
    }
}

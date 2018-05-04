package com.moesystems.contacts_parcial;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private FragmentViewer fragmentViewer;
    private Context mContext ;
    private ArrayList<Contacts> mData ;


    private static boolean fav = false;


    public ContactsAdapter(ArrayList<Contacts> contacts, Context cont) {
        this.mData = contacts;
        this.mContext = cont;
    }
    public ContactsAdapter(Context mContext, ArrayList<Contacts> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.contact_name.setText(mData.get(position).getName());
        holder.contact_image.setImageResource(mData.get(position).getImg());

        //set click listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    onClickCard(mData.get(position));
            }
        });
        if(mData.get(position).yesorno())
            holder.ib.setImageResource(R.drawable.yellow);
        else
            holder.ib.setImageResource(R.drawable.star);
        //boton para agregar y quitar
        holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoritos(position)){
                    holder.ib.setImageResource(R.drawable.yellow);
                    //Funcion del main que agrega a la lista de favoritos
                    ((MainActivity)mContext).addFavorites(mData.get(position));

                }
                else {
                    holder.ib.setImageResource(R.drawable.star);
                    //elimina de favoritos
                    ((MainActivity)mContext).quitar(mData.get(position).getName());

                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
    public boolean favoritos(int position){
        mData.get(position).set(!mData.get(position).yesorno());
        return mData.get(position).yesorno();
    }
    public void setTrue(){
        fav=true;
    }

    public void setFalse(){
        fav=false;
    }
    public boolean isOnFavorites() {
        return fav;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contact_name;
        ImageView contact_image;
        CardView cardView ;
        ImageButton ib;

        public MyViewHolder(View itemView) {
            super(itemView);

            contact_name = (TextView) itemView.findViewById(R.id.contact_name) ;
            contact_image = (ImageView) itemView.findViewById(R.id.contact_image);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            ib =  (ImageButton) itemView.findViewById(R.id.fav_vacio);


        }
    }

    public abstract void onClickCard (Contacts contacts);
}

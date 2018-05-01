package com.moesystems.contacts_parcial;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Contacts> mData ;


    public ContactsAdapter(Context mContext, List<Contacts> mData) {
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.contact_name.setText(mData.get(position).getName());
        holder.contact_image.setImageResource(mData.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contact_name;
        ImageView contact_image;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            contact_name = (TextView) itemView.findViewById(R.id.contact_name) ;
            contact_image = (ImageView) itemView.findViewById(R.id.contact_image);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }
}

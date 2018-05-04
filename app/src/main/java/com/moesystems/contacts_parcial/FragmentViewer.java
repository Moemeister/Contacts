package com.moesystems.contacts_parcial;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentViewer extends Fragment {
    TextView nombre,telefono;
    ImageView picture;
/*
    public static FragmentViewer newIntance (Contacts contacto){
        FragmentViewer fragment = new FragmentViewer();
        Bundle bundle = new Bundle();


        bundle.putSerializable(contacto);
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.viewer_fragment, container, false);

        //colocando los id de los textview y la imagen
        nombre =  view.findViewById(R.id.profile_name);
        telefono =  view.findViewById(R.id.profile_phone);
        picture = view.findViewById(R.id.profile_pic);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Contacts c = (Contacts) bundle.getSerializable("contactos");
            nombre.setText(c.getName());
            telefono.setText(c.getPhone());
            picture.setImageResource(c.getImg());
        }
        return view;
    }

}

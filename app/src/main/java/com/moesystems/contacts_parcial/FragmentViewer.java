package com.moesystems.contacts_parcial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentViewer extends Fragment {
    TextView nombre,telefono;
    ImageView picture;
    Contacts c;
    ImageView botonllamar,compartir,editar,borrar;
    String number;
    String getnumero,getnombre;
    FragmentListener mCallback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.viewer_fragment, container, false);

        //colocando los id de los textview y la imagen
        nombre =  view.findViewById(R.id.profile_name);
        telefono =  view.findViewById(R.id.profile_phone);
        picture = view.findViewById(R.id.profile_pic);
        botonllamar = view.findViewById(R.id.botonllamar);
        compartir =  view.findViewById(R.id.share);
        editar = view.findViewById(R.id.editar);
        borrar = view.findViewById(R.id.borrar);
        c = new Contacts();
        updateContact(c);

        botonllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallContact(getnumero);
            }
        });

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Nombre: "+getnombre+" Telefono: "+getnumero);
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onEdit();
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onDelete();
            }
        });



        return view;
    }
    public void updateContact (Contacts contact){
        nombre.setText(contact.getName());
        getnombre = contact.getName();
        telefono.setText(contact.getPhone());
        getnumero = contact.getPhone();
        picture.setImageResource(contact.getImg());

    }
    public void CallContact(String number) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" +number));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
    public interface FragmentListener {
        public void onEdit();
        public void onDelete();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;

    }
}

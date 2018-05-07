package com.moesystems.contacts_parcial;

import android.Manifest;
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
    ImageView botonllamar;
    String number;
    String getnumero,getnombre;
    ImageView compartir;

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
    public void Share2(View view)
    {
        Intent intent = getActivity().getIntent();
        Contacts contacts = intent.getExtras().getParcelable(Contacts.KEY_CONTACT);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"Nombre: "+contacts.getName()+" Telefono: "+contacts.getPhone());
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);


    }

}

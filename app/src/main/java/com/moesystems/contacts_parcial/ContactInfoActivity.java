package com.moesystems.contacts_parcial;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactInfoActivity extends AppCompatActivity {
    private TextView tvName, tvPhone;
    private ImageView img;
    private ImageView botonllamar;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        tvName = (TextView) findViewById(R.id.profile_name);
        tvPhone = (TextView) findViewById(R.id.profile_phone);
        img = (ImageView) findViewById(R.id.profile_pic);
        botonllamar = findViewById(R.id.botonllamar);

        //reciviendo los datos
        Intent intent = getIntent();
        Contacts contacts = intent.getExtras().getParcelable(Contacts.KEY_CONTACT);


        //setenado los valores
        tvName.setText(contacts.getName());
        tvPhone.setText(contacts.getPhone());
        img.setImageResource(contacts.getImg());
        number = contacts.getPhone();
        botonllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallContact(number);
            }
        });
    }

    public void CallContact(String number) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" +number));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
    public void Share(View view)
    {
        Intent intent = getIntent();
        Contacts contacts = intent.getExtras().getParcelable(Contacts.KEY_CONTACT);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"Nombre: "+contacts.getName()+" Telefono: "+contacts.getPhone());
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);


    }


}

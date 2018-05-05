package com.moesystems.contacts_parcial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddContacts extends AppCompatActivity {
    private EditText nombre, telefono;
    private ImageView img;
    private Contacts contacts;
    private Button boton;
    private Context mContext;
    private ArrayList<Contacts> mData;
    public static final String EXTRA_CONTACT = "com.moesystems.contacts.EXTRA_CONTACT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        nombre = (EditText) findViewById(R.id.txt_name_add);
        telefono = (EditText) findViewById(R.id.txt_phone_add);
        img = (ImageView) findViewById(R.id.add_picture);
        boton = (Button) findViewById(R.id.botonadd);

        Intent intent = getIntent();
        contacts = intent.hasExtra(EXTRA_CONTACT)? (Contacts)intent.getParcelableExtra(EXTRA_CONTACT): (new Contacts());

        nombre.setText(contacts.getName());
        telefono.setText(contacts.getPhone());
        img.setImageResource(R.drawable.empty_face);


    }
    public void saveContact(View view) {

        contacts.setName(nombre.getText().toString());
        contacts.setPhone(telefono.getText().toString());
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_CONTACT, contacts);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}

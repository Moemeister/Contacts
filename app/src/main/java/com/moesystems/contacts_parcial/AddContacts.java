package com.moesystems.contacts_parcial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
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

    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        nombre = (EditText) findViewById(R.id.txt_name_add);
        telefono = (EditText) findViewById(R.id.txt_phone_add);
        img = (ImageView) findViewById(R.id.add_picture);
        boton = (Button) findViewById(R.id.botonadd);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        Intent intent = getIntent();
        contacts = intent.hasExtra(EXTRA_CONTACT)? (Contacts)intent.getParcelableExtra(EXTRA_CONTACT): (new Contacts());

        nombre.setText(contacts.getName());
        telefono.setText(contacts.getPhone());
        img.setImageResource(R.drawable.empty_face);


    }
    public void saveContact(View view) {

        contacts.setName(nombre.getText().toString());
        contacts.setPhone(telefono.getText().toString());
        contacts.setImg(R.drawable.empty_face);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_CONTACT, contacts);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            img.setImageURI(imageUri);
        }else {
            img.setImageResource(R.drawable.empty_face);
        }
    }
}

package com.moesystems.contacts_parcial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {
    private TextView tvName,tvPhone;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        tvName = (TextView) findViewById(R.id.profile_name);
        tvPhone = (TextView) findViewById(R.id.profile_phone);
        img = (ImageView) findViewById(R.id.profile_pic);

        //reciviendo los datos
        Intent intent = getIntent();
        Contacts contacts = intent.getExtras().getParcelable(Contacts.KEY_CONTACT);


        //setenado los valores
        tvName.setText(contacts.getName());
        tvPhone.setText(contacts.getPhone());
        img.setImageResource(contacts.getImg());

    }
}

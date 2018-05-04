package com.moesystems.contacts_parcial;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contacts> list,favs;
    RecyclerView myrv;
    RecyclerView rv;
    ContactsAdapter myAdapter;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    Button btn1,btn2;
    ContactsAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        favs= new ArrayList<>();
        btn1 = findViewById(R.id.btn_contactos);
        btn2 = findViewById(R.id.btn_favoritos);
        addContacts();

        myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        myAdapter = new ContactsAdapter(this,list);
        //myrv.setLayoutManager(new GridLayoutManager(this,getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE? 2:3));
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                addContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void boton1_list(View v){
        myAdapter.setFalse();
       // btn1.setBackgroundColor(getResources().getColor(R.color.azul));
        //btn2.setBackgroundColor(getResources().getColor(R.color.skyblue));
        myAdapter = new ContactsAdapter(list,v.getContext());
        myrv.setAdapter(myAdapter);
    }
    public void boton2_favorites(View v){
        myAdapter.setTrue();
        //btn2.setBackgroundColor(getResources().getColor(R.color.azul));
       // btn1.setBackgroundColor(getResources().getColor(R.color.skyblue));
        myAdapter = new ContactsAdapter(favs,v.getContext());
        myrv.setAdapter(myAdapter);
    }

    public void addContacts() {
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                list.add(new Contacts(name, phoneNumber,R.drawable.empty_face));
            }
            phones.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addFavorites(Contacts favorites){
        favs.add(favorites);
    }
    public void quitar(String GameName){
        int i=0;

        for(Contacts game : favs){
            if(game.getName() == GameName){
                break;
            }
            i++;
        }
        favs.remove(i);
        if(myAdapter.isOnBookmark()){
            myAdapter = new ContactsAdapter(favs, this);
            myrv.setAdapter(myAdapter);
        }
    }
    
}

package com.moesystems.contacts_parcial;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
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
    FragmentViewer fragmentViewer;
    ContactsAdapter myAdapter;
    Contacts addedit;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    Button btn1,btn2;
    public static String KEY_LIST = "KEY_LIST";
    public static String KEY_LIST_FAVS = "KEY_LIST2";
    public static final int ADD_CONTACT = 2;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            list = savedInstanceState.getParcelableArrayList(KEY_LIST);
            favs= savedInstanceState.getParcelableArrayList(KEY_LIST_FAVS);
        }else{
            list = new ArrayList<>();
            favs= new ArrayList<>();
            addContacts();
        }

        btn1 = findViewById(R.id.btn_contactos);
        btn2 = findViewById(R.id.btn_favoritos);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentViewer = new FragmentViewer();
            fragmentTransaction.add(R.id.viewer, fragmentViewer);
            fragmentTransaction.commit();
        }
        myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        myAdapter = new ContactsAdapter(this,list){
            @Override
            public void onClickCard(Contacts contacts) {
                //pasando la informacion a la actividad
                intents(contacts);
            }
        };
        //myrv.setLayoutManager(new GridLayoutManager(this,getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE? 2:3));
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            myrv.setLayoutManager(new GridLayoutManager(this,2));
        }else{
            myrv.setLayoutManager(new GridLayoutManager(this,3));
        }
        myrv.setAdapter(myAdapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_LIST,list);
        outState.putParcelableArrayList(KEY_LIST_FAVS,favs);
        super.onSaveInstanceState(outState);
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
        myAdapter = new ContactsAdapter(list, v.getContext()) {
            @Override
            public void onClickCard(Contacts contacts) {
                //pasando la informacion a la actividad
                intents(contacts);
            }
        };
        myrv.setAdapter(myAdapter);
    }
    public void boton2_favorites(View v){
        myAdapter.setTrue();
        //btn2.setBackgroundColor(getResources().getColor(R.color.azul));
       // btn1.setBackgroundColor(getResources().getColor(R.color.skyblue));
        myAdapter = new ContactsAdapter(favs, v.getContext()) {
            @Override
            public void onClickCard(Contacts contacts) {
                //pasando la informacion a la actividad
                intents(contacts);
            }
        };
        myrv.setAdapter(myAdapter);
    }

    public void addContacts() {
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
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
    public void quitar(String ContactName){
        int i=0;

        for(Contacts con : favs){
            if(con.getName() == ContactName){
                break;
            }

            i++;
        }
        favs.remove(i);
        if(myAdapter.isOnFavorites()){
            myAdapter = new ContactsAdapter(favs, this) {
                @Override
                public void onClickCard(Contacts contacts) {
                    //pasando la informacion a la actividad
                    intents(contacts);
                }
            };
            myrv.setAdapter(myAdapter);
        }
    }

    public void intents(Contacts contacts){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(getApplicationContext(), ContactInfoActivity.class);
            intent.putExtra(Contacts.KEY_CONTACT, contacts);
            //iniciar la actividad
            startActivity(intent);
        }else if (getResources().getConfiguration().orientation ==  Configuration.ORIENTATION_LANDSCAPE){

            fragmentViewer.updateContact(contacts);

        }
    }
    /*public void addContacttoList(ContactsAdapter adapter, ArrayList<Contacts> contactList, int position){
        addedit = contactList.get(position);
        myAdapter = adapter;
        Intent intent = new Intent(this,AddContacts.class);
        intent.putExtra(AddContacts.EXTRA_CONTACT, addedit);
        startActivity(intent,ED);
    }*/
    public void agregarContacto(View view){
        Intent addIntent = new Intent(this, AddContacts.class);
        addedit = new Contacts();
        startActivityForResult(addIntent, ADD_CONTACT);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_CONTACT:
                if(resultCode == Activity.RESULT_OK) {
                    Contacts c = data.getParcelableExtra(AddContacts.EXTRA_CONTACT);
                    list.add(c);

                }
                addedit = null;
                break;

/*
            case EDIT_CONTACT:
                if(resultCode == Activity.RESULT_OK) {
                    Contact c = data.getParcelableExtra(EditActivity.EXTRA_CONTACT);
                    Toast.makeText(this, c.getName()+" "+c.getLastName(), Toast.LENGTH_SHORT).show();
                    allContactsFrag.getAdapter().updateContact(editing, c);
                    if(editing.isFavorite()) {
                        favContactsFrag.getAdapter().updateContact(editing, c);
                    }
                    else if(c.isFavorite()){
                        favContactsFrag.getAdapter().addContact(c);
                    }

                    editing_adapter.updateDialog(c);
                }
                editing = null;
                editing_adapter = null;
                break;*/
        }
    }
    
}

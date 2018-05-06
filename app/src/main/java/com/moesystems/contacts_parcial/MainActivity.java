package com.moesystems.contacts_parcial;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private static final int PERMISSIONS_REQUEST_CALL = 101;

    Button btn1,btn2;
    public static String KEY_LIST = "KEY_LIST";
    public static String KEY_LIST_FAVS = "KEY_LIST2";
    public static final int ADD_CONTACT = 2;
    public static final int EDIT_CONTACT = 1;
    private static boolean firstime=true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       // askForPermission(Manifest.permission.CALL_PHONE,0);
        //askForPermission(Manifest.permission.READ_CONTACTS,1);
        if (savedInstanceState != null){
            list = savedInstanceState.getParcelableArrayList(KEY_LIST);
            favs= savedInstanceState.getParcelableArrayList(KEY_LIST_FAVS);
        }else{
            list = new ArrayList<>();
            favs= new ArrayList<>();
            //addContacts();
        }
        //ASKING FOR PERMISSIONS
        // PERMISSION READ CONTACTS
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            addContacts();

        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        //PERMISSION CALLS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL);
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
        EditText filter = (EditText) findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                filterForContacts(editable.toString());
            }
        });
        myrv.setAdapter(myAdapter);

    }
    private void filterForContacts(String text){
        ArrayList<Contacts> filteredList = new ArrayList<>();
        for(Contacts item : list){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        myAdapter.filterList(filteredList);
    }
    private void filterForFavs(String text){
        ArrayList<Contacts> filteredList = new ArrayList<>();
        for(Contacts item : favs){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        myAdapter.filterList(filteredList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_LIST,list);
        outState.putParcelableArrayList(KEY_LIST_FAVS,favs);
        super.onSaveInstanceState(outState);
    }
    //METODO QUE SIRVE PARA VERIFICAR SI YA SE CONCEDIERON LOS PERMISOS Y LE SE MANDA EL ADAPTER NUEVANENTE PARA INFLAR
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[], @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addContacts();
                myrv.setAdapter(myAdapter);
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        EditText filter = (EditText) findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                filterForContacts(editable.toString());
            }
        });

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
        EditText filter = (EditText) findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                filterForFavs(editable.toString());
            }
        });
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
    public void editarContacto(ContactsAdapter adapter, ArrayList<Contacts> contactList, int position){
        addedit = contactList.get(position);
        myAdapter = adapter;
        Intent intent = new Intent(this,AddContacts.class);
        intent.putExtra(AddContacts.EXTRA_CONTACT, addedit);
        startActivityForResult(intent,EDIT_CONTACT);
    }
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


            case EDIT_CONTACT:
                if(resultCode == Activity.RESULT_OK) {
                    Contacts c = data.getParcelableExtra(AddContacts.EXTRA_CONTACT);
                    list.add(c);

                    break;
                }
        }
    }

    
}

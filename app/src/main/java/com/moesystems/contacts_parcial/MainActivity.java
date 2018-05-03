package com.moesystems.contacts_parcial;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Contacts> list;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    ContentResolver contactProvider;
    Cursor cursor;
    Cursor nCursor;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        addContacts();
        /*ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null,  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //list.add(new Contacts(cur.getString(0)+"",R.drawable.empty_face));
                Log.i("Names", name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    // Query phone here. Covered next
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Number", phoneNumber);
                        Log.i("lel", cur.getString(1)+"");
                        list.add(new Contacts(name+"",phoneNumber+"",R.drawable.empty_face));

                    }
                    phones.close();
                }

            }
        }*/
        /*ContentResolver contactProvider = getContentResolver();
        //Instanciando lector de cada fila, indicandole que columnas usar con el modelo anterior
        Cursor cursor = contactProvider.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        //Verificando que no esten vacias y que si hay valores, muestre
        if (cursor != null && cursor.getCount() > 0){
            //StringBuilder stringBuilderResult = new StringBuilder();
            //LLenando el recyclerview
            while (cursor.moveToNext()){
                list.add(new Contacts(cursor.getString(0)+"",R.drawable.empty_face));
            }
        }*/
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        ContactsAdapter myAdapter = new ContactsAdapter(this,list);
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

    public void addContacts() {
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            list.add(new Contacts("MONKA", "2342-2323",R.drawable.empty_face));
            list.add(new Contacts("MONKAOMEGA", "2334-2323",R.drawable.empty_face));
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
    
}

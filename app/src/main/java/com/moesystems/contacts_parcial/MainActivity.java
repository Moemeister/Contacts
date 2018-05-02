package com.moesystems.contacts_parcial;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
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

    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        list.add(new Contacts("The Vegitarian",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Wild Robot",R.drawable.ic_launcher_background));
        list.add(new Contacts("Maria Semples",R.drawable.ic_launcher_background));

        ContentResolver contactProvider = getContentResolver();
        //Instanciando lector de cada fila, indicandole que columnas usar con el modelo anterior
        Cursor cursor = contactProvider.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);

        //Verificando que no esten vacias y que si hay valores, muestre
        if (cursor != null && cursor.getCount() > 0){
            //StringBuilder stringBuilderResult = new StringBuilder();
            //LLenando el recyclerview
            while (cursor.moveToNext()){
                list.add(new Contacts(cursor.getString(0)+"",R.drawable.ic_launcher_background));
            }
        }
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        ContactsAdapter myAdapter = new ContactsAdapter(this,list);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
}

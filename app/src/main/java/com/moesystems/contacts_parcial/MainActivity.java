package com.moesystems.contacts_parcial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Contacts> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        list.add(new Contacts("The Vegitarian",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Wild Robot",R.drawable.ic_launcher_background));
        list.add(new Contacts("Maria Semples",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Martian",R.drawable.ic_launcher_background));
        list.add(new Contacts("He Died with...",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Vegitarian",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Wild Robot",R.drawable.ic_launcher_background));
        list.add(new Contacts("Maria Semples",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Martian",R.drawable.ic_launcher_background));
        list.add(new Contacts("He Died with...",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Vegitarian",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Wild Robot",R.drawable.ic_launcher_background));
        list.add(new Contacts("Maria Semples",R.drawable.ic_launcher_background));
        list.add(new Contacts("The Martian",R.drawable.ic_launcher_background));
        list.add(new Contacts("He Died with...",R.drawable.ic_launcher_background));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        ContactsAdapter myAdapter = new ContactsAdapter(this,list);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);


    }
    
}

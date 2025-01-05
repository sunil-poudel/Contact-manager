package com.example.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;
import UI.ContactsAdapter;

public class MainActivity extends AppCompatActivity {

    private ImageButton addContactButton;
    private RecyclerView recyclerView;
    private UI.ContactsAdapter contactsAdapter;
    private List<Contact> contactList;
    private Data.DatabaseHandler contactsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsDatabase = new DatabaseHandler(this);
        addContactButton = findViewById(R.id.contact_add);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        contactsDatabase.addContact(new Contact("Sunil Poudel", "XXXXXXXXXX"));
        contactList = contactsDatabase.getAllContacts();

        if(contactsAdapter==null) {
            contactsAdapter = new ContactsAdapter(this, contactList);
            recyclerView.setAdapter(contactsAdapter);
        } else{
            contactsAdapter.notifyDataSetChanged();
        }

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String addedName = intent.getStringExtra("Name");
        String addedPhoneNumber = intent.getStringExtra("Phone");
        contactsDatabase.addContact(new Contact(addedName, addedPhoneNumber));
    }
}
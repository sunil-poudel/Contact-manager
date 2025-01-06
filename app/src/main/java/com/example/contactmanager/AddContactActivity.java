package com.example.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Data.DatabaseHandler;
import Model.Contact;

public class AddContactActivity extends AppCompatActivity {
    private EditText addContactName;
    private EditText addContactPhone;
    private Button addContactSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addContactName = findViewById(R.id.editText_add_name);
        addContactPhone = findViewById(R.id.editText_add_phone);
        addContactSubmitButton = findViewById(R.id.button_add_submit);

        addContactSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addContactName.getText().toString();
                String phoneNumber = String.valueOf(addContactPhone.getText().toString());
                DatabaseHandler db = new DatabaseHandler(AddContactActivity.this);
                db.addContact(new Contact(name, phoneNumber));
                finish();
            }
        });


    }
}
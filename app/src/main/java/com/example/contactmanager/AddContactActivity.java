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

import java.time.LocalDateTime;
import java.util.Arrays;

public class AddContactActivity extends AppCompatActivity {
    private EditText addContactName;
    private EditText addContactPhone;
    private Button addContactSubmitButton;
    private static LocalDateTime modifiedAt;
    private static String[] dateTime;


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
                modifiedAt = LocalDateTime.now();
//                Log.d("MODIFIED TIME", String.valueOf(modifiedAt));
                dateTime = toDateTime(modifiedAt);
//                Log.d("DATE TIME", dateTime[0]+" "+dateTime[1]);
                String name = addContactName.getText().toString();
                String phoneNumber = addContactPhone.getText().toString();
                DatabaseHandler db = new DatabaseHandler(AddContactActivity.this);
                db.addContact(new Contact(name, phoneNumber, Arrays.toString(dateTime)));
                finish();

            }
        });


    }
    public String[] toDateTime(LocalDateTime localDateTime){
        //2025-01-06T09:43:51.710641
        char[] overallDate = localDateTime.toString().toCharArray();
        StringBuilder tempDate = new StringBuilder(), tempTime=new StringBuilder();
        String date;
        String time;
        for(int j=0; j<overallDate.length; j++){
            if(j<=9){
                tempDate.append(overallDate[j]);
            } else if(j>=11&&j<=15){
                tempTime.append(overallDate[j]);
            } else if(j!=10){
                break;
            }
        }
        date = String.valueOf(tempDate);
        time = String.valueOf(tempTime);
        return new String[]{date, time};
    }
}
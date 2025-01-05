package Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Util.UtilDb;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, UtilDb.DATABASE_NAME, null, UtilDb.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                UtilDb.TABLE_NAME,
                UtilDb.KEY_ID,
                UtilDb.KEY_NAME,
                UtilDb.KEY_PHONE_NUMBER);
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UtilDb.TABLE_NAME);
        onCreate(db);
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilDb.KEY_NAME, contact.getContactName());
        values.put(UtilDb.KEY_PHONE_NUMBER, contact.getContactPhoneNumber());

        db.insert(UtilDb.TABLE_NAME, null, values);
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                UtilDb.TABLE_NAME, new String[]{UtilDb.KEY_ID, UtilDb.KEY_NAME, UtilDb.KEY_PHONE_NUMBER}, UtilDb.KEY_ID+"=?",
                new String[]{String.valueOf(id)}, null, null, null, null
                );

        Contact contact = new Contact("XXXXX", "XXXXXXXXXX");
        if(cursor!=null) {
            cursor.moveToFirst();
            contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            cursor.close();
        }

        return contact;
    }

    public List<Contact> getAllContacts(){
        List<Contact> listOfcontacts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String SELECT_ALL = "SELECT * FROM "+UtilDb.TABLE_NAME;
        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setContactName(cursor.getString(1));
                contact.setContactPhoneNumber(cursor.getString(2));
                listOfcontacts.add(contact);
            } while(cursor.moveToNext());
        }

        cursor.close();;
        return listOfcontacts;
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(UtilDb.TABLE_NAME, UtilDb.KEY_ID+"=?", new String[]{String.valueOf(contact.getId())});

    }

    public int getContactCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        String getQuery = "SELECT * FROM "+UtilDb.TABLE_NAME;
        Cursor cursor = db.rawQuery(getQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UtilDb.KEY_NAME, contact.getContactName());
        values.put(UtilDb.KEY_PHONE_NUMBER, contact.getContactPhoneNumber());

        db.update(UtilDb.TABLE_NAME, values, UtilDb.KEY_ID+"=?", new String[]{String.valueOf(contact.getId())});

    }
}

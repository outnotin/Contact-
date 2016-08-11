package com.augmentis.ayp.contact.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.augmentis.ayp.contact.model.ContactDbSchema.ContactTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Noppharat on 8/11/2016.
 */
public class ContactLab {

    private static ContactLab instance;

    public static ContactLab getInstance(Context context){
        if(instance == null){
            instance = new ContactLab(context);
        }
        return instance;
    }

    public static ContentValues getContentValues(Contact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactTable.Cols.UUID, contact.getContactId().toString());
        contentValues.put(ContactTable.Cols.NAME, contact.getContactName());
        contentValues.put(ContactTable.Cols.TEL, contact.getContactTelNum());
        contentValues.put(ContactTable.Cols.EMAIL, contact.getContactEmail());
        return contentValues;
    }

    private Context context;
    private SQLiteDatabase database;

    private ContactLab(Context context){
        this.context = context.getApplicationContext();
        ContactBaseHelper contactBaseHelper = new ContactBaseHelper(context);
        database = contactBaseHelper.getWritableDatabase();
    }

    public ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs){
        Cursor cursor = database.query(ContactTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new ContactCursorWrapper(cursor);
    }

    public Contact getContactById(UUID uuid){
        ContactCursorWrapper cursor = queryContacts(ContactTable.Cols.UUID + " = ? ",
                new String[]{ uuid.toString()});
        try {
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getContact();
        }finally {
            cursor.close();
        }
    }

    public List<Contact> getContacts(){
        List<Contact> contacts = new ArrayList<>();
        ContactCursorWrapper cursor = queryContacts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return contacts;
    }

    public void addContact(Contact contact){
        ContentValues contentValues = getContentValues(contact);
        database.insert(ContactTable.NAME, null, contentValues);
    }

    public void deleteContact(UUID uuid){
        database.delete(ContactTable.NAME, ContactTable.Cols.UUID + " = ? ",
                new String[]{ uuid.toString()});
    }

    public void updateContact(Contact contact){
        String uuidStr = contact.getContactId().toString();
        ContentValues contentValues = getContentValues(contact);
        database.update(ContactTable.NAME, contentValues, ContactTable.Cols.UUID + " = ? ", new String[] { uuidStr });
    }

    public File getPhotoFile(Contact contact){
        File externalFileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFileDir == null){
            return null;
        }

        return new File(externalFileDir, contact.getPhotoFileName());
    }
}

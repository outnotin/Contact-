package com.augmentis.ayp.contact.model;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.augmentis.ayp.contact.model.ContactDbSchema.ContactTable;

import java.util.UUID;

/**
 * Created by Noppharat on 8/11/2016.
 */
public class ContactCursorWrapper extends CursorWrapper{

    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact(){
        String uuidString = getString(getColumnIndex(ContactTable.Cols.UUID));
        String name = getString(getColumnIndex(ContactTable.Cols.NAME));
        String tel = getString(getColumnIndex(ContactTable.Cols.TEL));
        String email = getString(getColumnIndex(ContactTable.Cols.EMAIL));

        Contact contact = new Contact(UUID.fromString(uuidString));
        contact.setContactName(name);
        contact.setContactTelNum(tel);
        contact.setContactEmail(email);

        return contact;
    }
}

package com.augmentis.ayp.contact.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.augmentis.ayp.contact.model.ContactDbSchema.ContactTable;

/**
 * Created by Noppharat on 8/11/2016.
 */
public class ContactBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contactBase.db";

    public ContactBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + ContactTable.NAME
        + "("
                + "_id integer primary key autoincrement, "
                + ContactTable.Cols.UUID + ", "
                + ContactTable.Cols.NAME + ", "
                + ContactTable.Cols.TEL + ", "
                + ContactTable.Cols.EMAIL + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

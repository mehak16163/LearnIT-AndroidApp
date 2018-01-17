package com.example.mehak.moreless;

/**
 * Created by mehak on 16/1/18.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class databasehelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME ="contact.db";
    private static final String TABLE_NAME ="contacts";
    private static final String COLUMN_NAME ="name";
    private static final String COLUMN_PASS="pass";
    private static final String COLUMN_EMAIL ="email";
    private static final String TABLE_CREATE="create table "+ TABLE_NAME+" ( name text not null , email text not null , pass text not null );";
    SQLiteDatabase db;

    public databasehelper(Context context){
        super(context , DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }
    public String searchPass(String s){
        db = this.getReadableDatabase();
        String query = "select name , pass from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a ,b;
        b = "not found";
        if (cursor.moveToFirst()){
            do{
                a = cursor.getString(0);
                if (a.equals(s)){
                    b = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return b;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertContact(contact c){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME , c.getName());
        values.put(COLUMN_EMAIL , c.getEmail());
        values.put(COLUMN_PASS , c.getPass());
        db.insert(TABLE_NAME , null, values);
        db.close();
    }
}
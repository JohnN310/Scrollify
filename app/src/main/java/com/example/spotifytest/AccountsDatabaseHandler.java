package com.example.spotifytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class AccountsDatabaseHandler extends SQLiteOpenHelper {

    private static final String databaseName = "accountsDatabase";
    private static final int databaseVersion = 1;
    private static final String tableName = "accounts";
    private static final String username = "username";
    private static final String password = "password";
    private static final String name = "name";
    private static final String code = "code";
    private static final String friends = "";

    public AccountsDatabaseHandler(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }
//    private static final List<String> friendList = new ArrayList<String>;


    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + tableName + " ("
                + username + " TEXT,"
                + password + " TEXT,"
                + name + " TEXT, "
                + code + " TEXT,"
                + friends + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }


    public void newUser(String userName, String userUsername, String userPassword, String userCode) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();
        String friendString = new String("");

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(username, userUsername);
        values.put(password, userPassword);
        values.put(name, userName);
        values.put(code, userCode);
        values.put(friends, friendString);

        // after adding all values we are passing
        // content values to our table.
        db.insert(tableName, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    public ArrayList<YourProfile> readProfiles() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase database = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor profileCursor = database.rawQuery("SELECT * FROM " + tableName, null);

        // on below line we are creating a new array list.
        ArrayList<YourProfile> profilesArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (profileCursor.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                profilesArrayList.add(new YourProfile(profileCursor.getString(1), profileCursor.getString(2), profileCursor.getString(3), profileCursor.getString(4)));
            } while (profileCursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        profileCursor.close();
        return profilesArrayList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }




}

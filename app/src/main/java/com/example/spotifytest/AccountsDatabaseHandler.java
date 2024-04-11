package com.example.spotifytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    private static final String friends = "friends";

    public AccountsDatabaseHandler(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + tableName + " ("
                + username + " TEXT,"
                + password + " TEXT,"
                + name + " TEXT,"
                + code + " TEXT,"
                + friends + " TEXT)";


        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }


    public void newUser(String userUsername, String userPassword, String userName, String userCode) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(username, userUsername);
        values.put(password, userPassword);
        values.put(name, userName);
        values.put(code, userCode);
        values.put(friends, "friends: ");

        // after adding all values we are passing
        // content values to our table.
        db.insert(tableName, null, values);
        // at last we are closing our
        // database after adding database.
    }

    public void changePassword(String userUsername, String newPassword) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(password, newPassword);

        Cursor cursor = db.rawQuery("Select * from databaseName where username = ?", new String[]{userUsername});
        if (cursor.getCount() > 0) {
            db.update(tableName, values, "username=?", new String[]{newPassword});

        }
    }

    public boolean contains(String username) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;

        }
        return false;

    }

    public int authenticate(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return 3;
        } else if (db.rawQuery("Select * from accounts where username = ?", new String[]{username}).getCount() > 0) {
            return 2;
        }
        return 1;

    }

    public YourProfile getAccount(String username) {

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("Select * from accounts where username = ? LIMIT 1", new String[]{username});
        YourProfile thisProfile = new YourProfile();

        int usernameInd = cursor.getColumnIndex("username");
        thisProfile.setUsername(cursor.getString(usernameInd));
        int passwordInd = cursor.getColumnIndex("password");
        thisProfile.setPassword(cursor.getString(passwordInd));
        int nameInd = cursor.getColumnIndex("name");
        thisProfile.setName(cursor.getString(nameInd));
        int friendsInd = cursor.getColumnIndex("friends");
        thisProfile.setFriends(cursor.getString(friendsInd));

        return thisProfile;
    }

    public void deleteAccount(String userUsername) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableName, "username=?", new String[]{userUsername});
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
                profilesArrayList.add(new YourProfile(profileCursor.getString(0), profileCursor.getString(1), profileCursor.getString(2), profileCursor.getString(3), profileCursor.getString(4)));
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

package com.example.spotifytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccountsDatabaseHandler extends SQLiteOpenHelper {

    private static final String databaseName = "accountsDatabase";
    private static final int databaseVersion = 1;
    private static final String tableName = "accounts";
    private static final String name = "name";
    private static final String username = "username";
    private static final String password = "password";
    private static final String token = "token";
    private static final String code = "code";

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
                + name + " TEXT, "
                + username + " TEXT,"
                + password + " TEXT,"
                + token + " TEXT,"
                + code + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }


    public void newUser(String userName, String userUsername, String userPassword, String userToken, String userCode) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(name, userName);
        values.put(username, userUsername);
        values.put(password, userPassword);
        values.put(token, userToken);
        values.put(code, userCode);

        // after adding all values we are passing
        // content values to our table.
        db.insert(tableName, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }




}

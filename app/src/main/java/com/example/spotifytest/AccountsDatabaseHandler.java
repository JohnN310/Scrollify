package com.example.spotifytest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;


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
    private static final String invites = "invites";
    private static final String savedWraps = "wraps";

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
                + friends + " TEXT,"
                + invites + " TEXT,"
                + savedWraps + " TEXT)";


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
        values.put(friends, "friends");
        values.put(invites, "invites");
        values.put(savedWraps, "wraps");

        // after adding all values we are passing
        // content values to our table.
        db.insert(tableName, null, values);
        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void changePassword(String userUsername, String newPassword) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(password, newPassword);

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{userUsername});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (userUsername.equals(cursor.getString(0))) {
                db.update(tableName, values, "username=?", new String[]{userUsername});
                break;
            }
            cursor.moveToNext();

        }
        db.close();
    }

    public void addInvite(String userUsername, String newFriend) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{userUsername});


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (userUsername.equals(cursor.getString(0))) {
                String prevInvites = cursor.getString(5);
                values.put(invites, prevInvites + "," + newFriend);
                db.update(tableName, values, "username=?", new String[]{userUsername});
                break;
            }
            cursor.moveToNext();

        }
        cursor.close();
        db.close();
    }

    public void removeInvite(String userUsername, String friendToRemove) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{userUsername});


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (userUsername.equals(cursor.getString(0))) {
                String prevInvites = cursor.getString(5);

                String newInvites;

                int beginning = prevInvites.indexOf("," + friendToRemove);
                // "invites,esha,david" -> beginning = 7

                if (!prevInvites.contains(friendToRemove + ",")) {
                    //if this invite is the last one
                    newInvites = prevInvites.substring(0, beginning);
                } else  {
                    int len = friendToRemove.length() + 1;
                    newInvites = prevInvites.substring(0, beginning) + prevInvites.substring(beginning + len);
                }

                System.out.println(newInvites);

                values.put(invites, newInvites);
                db.update(tableName, values, "username=?", new String[]{userUsername});
                break;

            }
            cursor.moveToNext();

        }

        cursor.close();
        db.close();
    }

    public void removeFriend(String userUsername, String friendToRemove) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{userUsername});


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (userUsername.equals(cursor.getString(0))) {
                String prevFriends = cursor.getString(4);

                String newFriends;

                int beginning = prevFriends.indexOf("," + friendToRemove);
                // "invites,esha,david" -> beginning = 7

                if (!prevFriends.contains(friendToRemove + ",")) {
                    //if this invite is the last one
                    newFriends = prevFriends.substring(0, beginning);
                } else  {
                    int len = friendToRemove.length() + 1;
                    newFriends = prevFriends.substring(0, beginning) + prevFriends.substring(beginning + len);
                }

                System.out.println(newFriends);

                values.put(friends, newFriends);
                db.update(tableName, values, "username=?", new String[]{userUsername});
                break;

            }
            cursor.moveToNext();

        }

        cursor.close();
        db.close();
    }

    public void addWrap(String username, String data) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{username});


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (username.equals(cursor.getString(0))) {
                String prevWraps = cursor.getString(6);
                values.put(savedWraps, prevWraps + "," + data );
                db.update(tableName, values, "username=?", new String[]{username});
                break;
            }
            cursor.moveToNext();

        }
        cursor.close();
        db.close();
    }

    public void addFriend(String userUsername, String newFriend) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{userUsername});


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (userUsername.equals(cursor.getString(0))) {
                String prevFriends = cursor.getString(4);
                values.put(friends, prevFriends + "," + newFriend);
                db.update(tableName, values, "username=?", new String[]{userUsername});
                break;
            }
            cursor.moveToNext();

        }

        cursor.close();
        db.close();
    }

    public boolean contains(String username) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            db.close();

            System.out.println(username + " exists!");
            return true;

        }
        db.close();
        System.out.println("nonexistent");
        return false;

    }

    public int authenticate(String username, String password) {

        if (username.equals("admin") && password.equals("admin")) {
            return 4;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{username});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (username.equals(cursor.getString(0))) {
                if (cursor.getString(1).equals(password)) {
                    return 3;
                } else if (!cursor.getString(1).equals(password)) {
                    return 2;
                }
            }

            cursor.moveToNext();

        }

        cursor.close();
        db.close();

        return 1;

    }


    public YourProfile getAccount(String userName) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from accounts where username = ?", new String[]{userName});
        YourProfile thisProfile = new YourProfile();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if (userName.equals(cursor.getString(0))) {
                thisProfile.setUsername(cursor.getString(0));
                thisProfile.setPassword(cursor.getString(1));
                thisProfile.setName(cursor.getString(2));
                thisProfile.setCode(cursor.getString(3));
                thisProfile.setFriends(cursor.getString(4));
                thisProfile.setInvites(cursor.getString(5));
                thisProfile.setWraps(cursor.getString(6));
                break;
            }
            cursor.moveToNext();

        }

        cursor.close();
        db.close();

        return thisProfile;


    }

    public void deleteAccount(String userUsername) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableName, "username=?", new String[]{userUsername});
        db.close();
    }


    public List<String> readProfiles() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from accounts", null);
        List<String> profiles = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                String thisProfile = cursor.getString(0) + " - ";
                thisProfile += cursor.getString(2);
                profiles.add(thisProfile);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return profiles;

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }




}

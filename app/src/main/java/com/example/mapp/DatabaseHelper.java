package com.example.mapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context,"Login1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(username text primary key, password text)");
        db.execSQL("Create table movie(title text primary key, year integer, rating integer, description text)");
        db.execSQL("Create table usermovie(username text, movie text, PRIMARY KEY(username, movie))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists movie");
        db.execSQL("drop table if exists usermovie");
    }

    public Boolean deleteUserMovie(String username, String title){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("DELETE FROM usermovie WHERE username = ? and movie = ?", new String[]{username,title});
        if(cursor.getCount()>0){
            return true;
        }
        return false;
    }

    public ArrayList<Movie> getAllMovies(){
        ArrayList<Movie> movies = new
                ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from movie",null);
        if(cursor != null && cursor.moveToFirst()){
            do {
                Movie nm = new Movie(cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getInt(cursor.getColumnIndex("year")),
                        cursor.getInt(cursor.getColumnIndex("rating")),
                        cursor.getString(cursor.getColumnIndex("description")));
                movies.add(nm);
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public ArrayList<Movie> getUserMovies(String username){
        ArrayList<Movie> movies = new
                ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from movie m left join usermovie um on um.movie = m.title where um.username = ?",new String[]{username});
        if(cursor != null && cursor.moveToFirst()){
            do {
                Movie nm = new Movie(cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getInt(cursor.getColumnIndex("year")),
                        cursor.getInt(cursor.getColumnIndex("rating")),
                        cursor.getString(cursor.getColumnIndex("description")));
                movies.add(nm);
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public boolean insertUserMovie(String username, String title){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("movie", title);

        Cursor cursor = database.rawQuery("Select * from usermovie where username=? and movie=?",new String[]{username,title});
        if(cursor.getCount()>0){
            return false;
        }
        long ins = database.insert("usermovie",null,contentValues);
        if(ins==-1){
            return false;
        }
        return false;
    }

    public boolean insertUser(String username, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long ins = database.insert("user",null,contentValues);
        if(ins==-1){
            return false;
        }
        return true;

    }

    public boolean updateMovie(String title, int year, int rating, String description){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Update movie set title=?, year=?, rating=?, description=? where title=?",new String[]{title, Integer.toString(year), Integer.toString(rating), description, title});
        if(cursor.getCount()>0){
            return false;
        }
        return true;

    }

    public boolean insertMovie(String title, int year, int rating, String description){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("year", year);
        contentValues.put("rating", rating);
        contentValues.put("description", description);
        long ins = database.insert("movie",null,contentValues);
        if(ins==-1){
            return false;
        }
        return true;

    }

    public Boolean checkMovie(String title){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from movie where title=?",new String[]{title});
        if(cursor.getCount()>0){
            return false;
        }
        return true;
    }


    public Boolean checkUsername(String username){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where username=?",new String[]{username});
        if(cursor.getCount()>0){
            return false;
        }
        return true;
    }

    public Boolean checkLogin(String username, String password){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where username=? and password=?",new String[]{username,password});
        if(cursor.getCount()>0){
            return true;
        }
        return false;
    }

    public Boolean deleteMovie(String title) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("DELETE FROM movie WHERE title = ?", new String[]{title});
        Cursor cursor1 = database.rawQuery("DELETE FROM usermovie WHERE movie = ?", new String[]{title});
        if(cursor.getCount()>0 || cursor1.getCount()>0){
            return false;
        }
        return true;
    }
}

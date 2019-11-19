package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyMovies extends AppCompatActivity {
    public ArrayList<Movie> movies = new ArrayList<>();
    private ListView list;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_movies);
        databaseHelper = new DatabaseHelper(this);
        Bundle b = getIntent().getExtras();
        final String username = b.getString("username");
        list = (ListView)findViewById(R.id.list);
        this.movies = databaseHelper.getUserMovies(username);
        AdapterMyList arrayAdapter = new AdapterMyList(this,movies,username,databaseHelper);
        list.setAdapter(arrayAdapter);
    }
}

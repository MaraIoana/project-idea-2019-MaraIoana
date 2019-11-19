package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LW extends AppCompatActivity {
    public ArrayList<Movie> movies = new ArrayList<>();
    private ListView list;
    DatabaseHelper databaseHelper;
    Button myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        databaseHelper = new DatabaseHelper(this);

        Bundle b = getIntent().getExtras();
        final String username = b.getString("username");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Welcome, " +username+"!");

        list = (ListView)findViewById(R.id.list);

        movies = databaseHelper.getAllMovies();

        CustomAdapter arrayAdapter = new CustomAdapter(this,movies,username,databaseHelper);

        list.setAdapter(arrayAdapter);

        myList = (Button) findViewById(R.id.mylist);

        myList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LW.this,MyMovies.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });



    }
}

package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LWA extends AppCompatActivity {

    public ArrayList<Movie> movies = new ArrayList<>();
    EditText title, year, rating, description;
    private ListView list;
    DatabaseHelper databaseHelper;
    Button addMovie, updateMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_admin);

        databaseHelper = new DatabaseHelper(this);

        list = (ListView)findViewById(R.id.list);
        addMovie = (Button) findViewById(R.id.addButton);
        updateMovie = (Button) findViewById(R.id.updateButton);
        title = (EditText)findViewById(R.id.title);
        year = (EditText)findViewById(R.id.year);
        rating = (EditText)findViewById(R.id.rating);
        description = (EditText)findViewById(R.id.description);


        movies = databaseHelper.getAllMovies();

        AdminAdapter arrayAdapter = new AdminAdapter(this,movies,databaseHelper);

        updateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ttl = title.getText().toString();
                String descr = description.getText().toString();
                Boolean validYear = true;
                Boolean validRating = true;
                int yr=0,ra=0;
                try
                {
                    // the String to int conversion happens here
                    yr = Integer.parseInt(year.getText().toString().trim());
                    // print out the value after the conversion
                }
                catch (NumberFormatException nfe)
                {
                    validYear = false;
                    Toast.makeText(getApplicationContext(),"NumberFormatException: " + nfe.getMessage(),Toast.LENGTH_SHORT).show();
                }
                try
                {
                    // the String to int conversion happens here
                    ra = Integer.parseInt(rating.getText().toString().trim());
                    // print out the value after the conversion
                }
                catch (NumberFormatException nfe)
                {
                    validRating = false;
                    Toast.makeText(getApplicationContext(),"NumberFormatException: " + nfe.getMessage(),Toast.LENGTH_SHORT).show();
                }
                if(ttl.equals("") || descr.equals("") || validYear == false || validRating == false){
                    Toast.makeText(getApplicationContext(),"Unable to add, please check the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean updated = databaseHelper.updateMovie(ttl,yr,ra,descr);
                    if(updated==true){
                        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Couldn't update",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ttl = title.getText().toString();
                String descr = description.getText().toString();
                Boolean validYear = true;
                Boolean validRating = true;
                int yr=0,ra=0;
                try
                {
                    // the String to int conversion happens here
                    yr = Integer.parseInt(year.getText().toString().trim());
                    // print out the value after the conversion
                }
                catch (NumberFormatException nfe)
                {
                    validYear = false;
                    Toast.makeText(getApplicationContext(),"NumberFormatException: " + nfe.getMessage(),Toast.LENGTH_SHORT).show();
                }
                try
                {
                    // the String to int conversion happens here
                    ra = Integer.parseInt(rating.getText().toString().trim());
                    // print out the value after the conversion
                }
                catch (NumberFormatException nfe)
                {
                    validRating = false;
                    Toast.makeText(getApplicationContext(),"NumberFormatException: " + nfe.getMessage(),Toast.LENGTH_SHORT).show();
                }
                if(ttl.equals("") || descr.equals("") || validYear == false || validRating == false){
                    Toast.makeText(getApplicationContext(),"Unable to add, please check the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean inserted = databaseHelper.insertMovie(ttl,yr,ra,descr);
                    if(inserted==true){
                        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Movie with same title might already exist",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        list.setAdapter(arrayAdapter);

    }
}

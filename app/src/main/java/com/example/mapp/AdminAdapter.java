package com.example.mapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private List<Movie> moviesList;
    private DatabaseHelper databaseHelper;

    public AdminAdapter(Context context, ArrayList<Movie> list, DatabaseHelper dbHelper) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
        databaseHelper = dbHelper;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_admin,parent,false);

        final Movie currentMovie = moviesList.get(position);
        Button deleteBtn = (Button)listItem.findViewById(R.id.delete);

        TextView title = (TextView) listItem.findViewById(R.id.title);
        title.setText(currentMovie.getTitle());

        TextView year = (TextView) listItem.findViewById(R.id.year);
        year.setText("Year: "+(String.valueOf(currentMovie.getYear())));

        TextView rating = (TextView) listItem.findViewById(R.id.rating);
        rating.setText("Rating: "+(String.valueOf(currentMovie.getRating())));

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean deleted = databaseHelper.deleteMovie(currentMovie.getTitle());
                if(deleted == true)
                {
                    Toast.makeText(mContext,"Could not delete.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext,"Successfully deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return listItem;
    }

}
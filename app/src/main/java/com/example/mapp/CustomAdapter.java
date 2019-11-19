package com.example.mapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class CustomAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private List<Movie> moviesList;
    private String username;
    private DatabaseHelper databaseHelper;

    public CustomAdapter(Context context, ArrayList<Movie> list, String usr, DatabaseHelper dbHelper) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
        username = usr;
        databaseHelper = dbHelper;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        final Movie currentMovie = moviesList.get(position);
        Button addBtn = (Button)listItem.findViewById(R.id.add);
        Button info = (Button)listItem.findViewById(R.id.info);

        TextView title = (TextView) listItem.findViewById(R.id.title);
        title.setText(currentMovie.getTitle());

        TextView year = (TextView) listItem.findViewById(R.id.year);
        year.setText("Year: "+(String.valueOf(currentMovie.getYear())));

        TextView rating = (TextView) listItem.findViewById(R.id.rating);
        rating.setText("Rating: "+(String.valueOf(currentMovie.getRating())));

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean inserted = databaseHelper.insertUserMovie(username,currentMovie.getTitle());
                if(inserted==true)
                {
                    Toast.makeText(mContext,"added",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext,"not added",Toast.LENGTH_SHORT).show();
                }

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(currentMovie.getTitle());
                alertDialog.setMessage(currentMovie.getDescription());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

        return listItem;
    }

}

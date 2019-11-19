package com.example.mapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterMyList extends ArrayAdapter<Movie> {
    private Context mContext;
    private List<Movie> moviesList;
    private String username;
    private DatabaseHelper databaseHelper;

    public AdapterMyList(Context context, ArrayList<Movie> list, String usr, DatabaseHelper dbHelper) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
        username = usr;
        databaseHelper = dbHelper;

    }
    public void ShowDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(mContext);

        LinearLayout linearLayout = new LinearLayout(mContext);
        final RatingBar rating = new RatingBar(mContext);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);

        //add ratingBar to linearLayout
        linearLayout.addView(rating);


        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Add Rating: ");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);



        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
            }
        });



        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.delete_list_item,parent,false);

        final Movie currentMovie = moviesList.get(position);
        Button deleteBtn = (Button)listItem.findViewById(R.id.delete);
        Button info = (Button)listItem.findViewById(R.id.info);

        TextView title = (TextView) listItem.findViewById(R.id.title);
        title.setText(currentMovie.getTitle());

        TextView year = (TextView) listItem.findViewById(R.id.year);
        year.setText("Year: "+(String.valueOf(currentMovie.getYear())));

        TextView rating = (TextView) listItem.findViewById(R.id.rating);
        rating.setText("Rating: "+(String.valueOf(currentMovie.getRating())));

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ShowDialog();
                Boolean deleted = databaseHelper.deleteUserMovie(username,currentMovie.getTitle());
                if(deleted == true)
                {
                    Toast.makeText(mContext,"Could not delete.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext,"Successfully deleted",Toast.LENGTH_SHORT).show();
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
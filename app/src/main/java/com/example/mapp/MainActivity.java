package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText username, password, password2;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        password2=(EditText)findViewById(R.id.pass);
        register=(Button)findViewById(R.id.button);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString();
                String pass = password.getText().toString();
                String pass2 = password2.getText().toString();
                if(usr.equals("")||pass.equals("") || pass2.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(pass2)){
                        Boolean checkUser = databaseHelper.checkUsername(usr);
                        if(checkUser==true){
                            Boolean inserted = databaseHelper.insertUser(usr,pass);
                            if(inserted==true){
                                Toast.makeText(getApplicationContext(),"Register successfully!",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this,Login.class);
                                startActivity(i);
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Username already in use",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

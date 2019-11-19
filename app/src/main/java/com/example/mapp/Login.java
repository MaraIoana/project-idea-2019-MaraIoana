package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString();
                String pass = password.getText().toString();
                Boolean check = databaseHelper.checkLogin(usr,pass);
                if(check==true){
                    System.out.println(username);
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    if(usr.equals("admin")){
                        Intent i = new Intent(Login.this, LWA.class);
                        startActivity(i);
                    }
                    else{
                        Intent i = new Intent(Login.this, LW.class);
                        i.putExtra("username",usr);
                        startActivity(i);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Auth failure",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

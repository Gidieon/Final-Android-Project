package com.example.thequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        Button register = findViewById(R.id.register);
        register.setOnClickListener(view -> startActivity(new Intent(Login.this,
                Register.class)));
        Button login = findViewById(R.id.login);
        login.setOnClickListener(view -> startActivity(new Intent(Login.this,
                GetStarted.class)));
    }


}
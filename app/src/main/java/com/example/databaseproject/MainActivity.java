package com.example.databaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button couchbaseLiteButton;
    private Button realmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        realmButton = (Button) findViewById(R.id.realmButton);
        couchbaseLiteButton = (Button) findViewById(R.id.couchbaseLiteButton);

        realmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RealmMenu.class));
            }
        });

        couchbaseLiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CouchbaseLiteMenu.class));
            }
        });
    }
}
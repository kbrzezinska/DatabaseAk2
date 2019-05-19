package com.example.databaseproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmResults;

public class DBGenerator extends AppCompatActivity {

    private Button refreshViewButton;
    private Button prevActivityButton;
    private ListView itemsListView;
    private Realm realm;
    private List<String> dataList;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbgenerator);

        id = 0;
        realm = Realm.getDefaultInstance();
        refreshViewButton = (Button) findViewById(R.id.refreshViewButton);
        prevActivityButton = (Button) findViewById(R.id.prevActivityButton);
        itemsListView = (ListView) findViewById(R.id.itemsListView);

        refreshViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Person> resultPersons = realm.where(Person.class).findAll();
                dataList = new ArrayList<>();
                for(int i = 0; i < resultPersons.size(); i++) {
                    dataList.add(resultPersons.get(i).toString());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DBGenerator.this, android.R.layout.simple_list_item_1, dataList);
                itemsListView.setAdapter(arrayAdapter);
            }
        });

        prevActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
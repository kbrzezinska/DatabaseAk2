package com.example.databaseproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CouchbaseLiteDisplay extends AppCompatActivity {

    private Button refreshViewButton;
    private Button prevActivityButton;
    private ListView itemsListView;
    private Realm realm;
    private List<String> dataList;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couchbase_lite_display);

//        id = 0;
//        realm = Realm.getDefaultInstance();
//        refreshViewButton = (Button) findViewById(R.id.refreshViewButton2);
//        prevActivityButton = (Button) findViewById(R.id.prevActivityButton2);
//        itemsListView = (ListView) findViewById(R.id.itemsListView2);
//
//        refreshViewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RealmResults<Person> resultPersons = realm.where(Person.class).findAll();
//                dataList = new ArrayList<>();
//                for(int i = 0; i < resultPersons.size(); i++) {
//                    dataList.add(resultPersons.get(i).toString());
//                }
//
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CouchbaseLiteDisplay.this,
//                                                    android.R.layout.simple_list_item_2, dataList);
//                itemsListView.setAdapter(arrayAdapter);
//            }
//        });
//
//        prevActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}

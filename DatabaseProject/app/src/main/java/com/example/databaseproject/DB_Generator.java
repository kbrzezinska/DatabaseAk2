package com.example.databaseproject;

import android.R.layout;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.layout.*;

public class DB_Generator extends AppCompatActivity {

    private Button generateButton;
    private Button refreshViewButton;
    private Button prevActivityButton;
    private Button DB_deleteButton;
    private ListView itemsListView;
    private Realm realm;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;
    private EditText txtInput;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db__generator);

        id = 0;
        realm = Realm.getDefaultInstance();
        generateButton = (Button) findViewById(R.id.generateButton);
        refreshViewButton = (Button) findViewById(R.id.refreshViewButton);
        prevActivityButton = (Button) findViewById(R.id.prevActivityButton);
        DB_deleteButton = (Button) findViewById(R.id.DB_deleteButton);

        itemsListView = (ListView) findViewById(R.id.itemsListView);

        DB_deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        Random randGen = new Random();
                        String colors[] = {"Black", "White", "Brown", "Blond", "Red", "Grey"};

                        for (int i = 0; i < 1000; ++i) {

                            Person person = bgRealm.createObject(Person.class);
                            person.FirstName = "Name" + i;
                            person.LastName = "Surname" + i;
                            person.age = randGen.nextInt(20);
                            person.id = id;
                            id++;
                            Dog dog = bgRealm.createObject(Dog.class);
                            dog.name = "DogName" + i;
                            dog.age = randGen.nextInt(20);
                            dog.color = colors[randGen.nextInt(6)];
                            person.dogs.add(dog);
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        id++;
                        System.out.println("Success");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        System.out.println("Error");
                    }
                });
            }
        });

        refreshViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Person> resultPersons = realm.where(Person.class).findAll();
                dataList = new ArrayList<>();
                for(int i = 0; i < resultPersons.size(); i++) {
                    dataList.add(resultPersons.get(i).toString());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DB_Generator.this, android.R.layout.simple_list_item_1, dataList);
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

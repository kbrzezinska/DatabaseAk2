package com.example.databaseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private EditText FirstName;
    private EditText SurName;
    private EditText age;
    private EditText city;
    private EditText dogName;
    private EditText s;
    private EditText color;
    private EditText dogAge;
    private Realm realm;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        RealmResults<Person> results = realm.where(Person.class).findAll();
        if (!results.isEmpty()) {
            id = results.max("id").intValue();
            id++;
        } else {
            id = 1;
        }
        System.out.println("id " + id);
        FirstName = findViewById(R.id.textViewName);
        SurName = findViewById(R.id.textViewSurname);
        age = findViewById(R.id.textViewAge);
        s = findViewById(R.id.textViewS);
        dogName = findViewById(R.id.textViewDogName);
        dogAge = findViewById(R.id.textViewDogAge);
        color = findViewById(R.id.textViewDogColor);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                writeToDB(FirstName.getText().toString(), SurName.getText().toString(), age.getText().toString(), dogName.getText().toString(), dogAge.getText().toString(), color.getText().toString());
                ShowDate();
            }
        });

        Button nextActivityButton = (Button) findViewById(R.id.nextActivityButton);
        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DB_Generator.class));
            }
        });
    }

    public void ShowDate() {
        //RealmResults<Person> dogsWithTeenagers=realm.where(Person.class).between("age", 13, 20).equalTo("dogs.age", 1).findAll();
        RealmResults<Person> dogsWithTeenagers = realm.where(Person.class).findAll();

        for (Person d : dogsWithTeenagers) {
            for (Dog dd : d.dogs) {
                System.out.println(dd.name + " " + dd.age + " " + dd.color);
            }
        }
    }

    public void writeToDB(final String name, final String surname, final String age, final String dogName, final String dogAge, final String color) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.FirstName = name;
                person.LastName = surname;
                person.age = Integer.parseInt(age);
                person.id = id;

                Dog dog = bgRealm.createObject(Dog.class);
                dog.name = dogName;
                dog.age = Integer.parseInt(dogAge);
                dog.color = color;
                person.dogs.add(dog);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                id++;
                s.setText("success");
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                s.setText("error");
                // Transaction failed and was automatically canceled.
            }
        });
    }
}


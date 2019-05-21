package com.example.databaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmMenu extends AppCompatActivity {

    private Button nextActivityButton;
    private Button generateButton;
    private Button deleteButton;
    private Button editButton;
    private Button findButton;
    private TextView timeTextView;
    private ListView itemsListView;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;
    private EditText txtInput;
    private Realm realm;
    private long start;
    private long time;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.realm_menu);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        id = 0;
        realm = Realm.getDefaultInstance();
        nextActivityButton = (Button) findViewById(R.id.nextActivityButton);
        generateButton = (Button) findViewById(R.id.genButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        editButton = (Button) findViewById(R.id.editButton);
        findButton = (Button) findViewById(R.id.findButton);
        timeTextView = (TextView) findViewById(R.id.timeTextView);

        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RealmMenu.this, RealmDisplay.class));
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randGen = new Random();
                String colors[] = {"Black", "White", "Brown", "Blond", "Red", "Grey"};

                start = System.currentTimeMillis();
                for (int i = 0; i < 1000; ++i) {
                    realm.beginTransaction();
                    Person person = realm.createObject(Person.class);
                    person.FirstName = "Name" + i;
                    person.LastName = "Surname" + i;
                    person.age = randGen.nextInt(20);
                    person.id = id;
                    id++;
                    Dog dog = realm.createObject(Dog.class);
                    dog.name = "DogName" + i;
                    dog.age = randGen.nextInt(20);
                    dog.color = colors[randGen.nextInt(6)];
                    person.dogs.add(dog);
                    realm.commitTransaction();
                }

                time = System.currentTimeMillis() - start;
                System.out.println(time);
                timeTextView.setText(Long.toString(time));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmResults<Person> resultPersons = realm.where(Person.class).findAll();
                // System.out.println(resultPersons.size());
                start = System.currentTimeMillis();
                while (resultPersons.size() != 0){
                    realm.beginTransaction();
                    resultPersons.deleteFirstFromRealm();
                    realm.commitTransaction();
                }
                time = System.currentTimeMillis()-start;System.out.println(time);
                timeTextView.setText(Long.toString(time));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmResults<Person> resultPersons = realm.where(Person.class).findAll();
                start = System.currentTimeMillis();
                for(int i = 0; i < resultPersons.size(); i++) {
                    realm.beginTransaction();
                    resultPersons.get(i).FirstName="Ania";
                    realm.commitTransaction();
                }
                time = System.currentTimeMillis()-start;System.out.println(time);
                timeTextView.setText(Long.toString(time));
            }

        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = System.currentTimeMillis();
                RealmResults<Person> resultPersons = realm.where(Person.class).equalTo("age", 19
                ).findAll();
                time = System.currentTimeMillis()-start;System.out.println(time);
                timeTextView.setText(Long.toString(time));
            }

        });

                  /*  Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                writeToDB(FirstName.getText().toString(), SurName.getText().toString(), age.getText().toString(), dogName.getText().toString(), dogAge.getText().toString(), color.getText().toString());
                ShowDate();
            }
        });
*/
    }
}

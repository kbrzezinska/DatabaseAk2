package com.example.databaseproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private Button generateButton;
    private Button refreshViewButton;
    private Button editButton;
    private Button DB_deleteButton;
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

        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        id = 0;
        realm = Realm.getDefaultInstance();
        generateButton = (Button) findViewById(R.id.gen);
        refreshViewButton = (Button) findViewById(R.id.refresh);

        DB_deleteButton = (Button) findViewById(R.id.delete);

        DB_deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmResults<Person> resultPersons = realm.where(Person.class).findAll();
                // System.out.println(resultPersons.size());
                start = System.currentTimeMillis();
                while (resultPersons.size()!=0){
                    for(int i = 0; i < resultPersons.size(); i++) {
                        realm.beginTransaction();
                        //  resultPersons.deleteAllFromRealm();
                        resultPersons.get(i).deleteFromRealm();
                        realm.commitTransaction();
                    }}
                time=System.currentTimeMillis()-start;System.out.println(time);

            }
        });


        editButton = (Button) findViewById(R.id.edit);

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


        itemsListView = (ListView) findViewById(R.id.List);

      /*  Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                writeToDB(FirstName.getText().toString(), SurName.getText().toString(), age.getText().toString(), dogName.getText().toString(), dogAge.getText().toString(), color.getText().toString());
                ShowDate();
            }
        });
*/
        Button nextActivityButton = (Button) findViewById(R.id.nextActivityButton);
        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DB_Generator.class));
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
                time=System.currentTimeMillis()-start;System.out.println(time);
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, dataList);
                itemsListView.setAdapter(arrayAdapter);
            }
        });

    }
}
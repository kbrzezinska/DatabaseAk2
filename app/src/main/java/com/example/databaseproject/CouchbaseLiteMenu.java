package com.example.databaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.Expression;
import com.couchbase.lite.Meta;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.SelectResult;
import java.util.List;
import java.util.Random;

public class CouchbaseLiteMenu extends AppCompatActivity {

    private Database database;
    private Button nextActivityButton;
    private Button generateButton;
    private Button deleteButton;
    private Button editButton;
    private Button findButton;
    private TextView timeTextView;
    private long start;
    private long time;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_menu);

        // Get the database (and create it if it doesn’t exist).
        DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
        config.setDirectory(String.format("%s/%s", getApplicationContext().getFilesDir(),"BazaCouch"));
        database = null;
        try {
            database = new Database("mydb", config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        id = 0;
        nextActivityButton = (Button) findViewById(R.id.nextActivityButton);
        generateButton = (Button) findViewById(R.id.genButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        editButton = (Button) findViewById(R.id.editButton);
        findButton = (Button) findViewById(R.id.findButton);
        timeTextView = (TextView) findViewById(R.id.timeTextView);

        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CouchbaseLiteMenu.this, CouchbaseLiteDisplay.class));
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randGen = new Random();
                String colors[] = {"Black", "White", "Brown", "Blond", "Red", "Grey"};

                start = System.currentTimeMillis();
                for (int i = 0; i < 1000; ++i) {
                    //Tworzenie nowego rekordu do bazy
                    MutableDocument mutableDoc = new MutableDocument();
                    mutableDoc.setString("FirstName", "Name"+i);
                    mutableDoc.setString("LastName", "Surname"+i);
                    mutableDoc.setInt("Age", randGen.nextInt(20));
                    mutableDoc.setInt("Id", id);
                    id++;
                    mutableDoc.setString("DogName", "Dogname"+i);
                    mutableDoc.setInt("DogAge", randGen.nextInt(20));
                    mutableDoc.setString("DogColor", colors[randGen.nextInt(6)]);

                    try {
                        database.save(mutableDoc);
                    } catch (CouchbaseLiteException e) {
                        e.printStackTrace();
                    }
                }

                time = System.currentTimeMillis() - start;
                System.out.println(time);
                timeTextView.setText(Long.toString(time));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = QueryBuilder.select(SelectResult.expression(Meta.id))
                        .from(DataSource.database(database));

                try {
                   List<Result> results = query.execute().allResults();

                    start = System.currentTimeMillis();
                    for(int i = 0; i < results.size(); ++i) {
                        Document doc = database.getDocument(results.get(i).getString("id"));
                        //Log.d("CouchbaseLite Database:", doc.getString("FirstName"));
                        database.delete(doc);
                    }
                    time = System.currentTimeMillis()-start;
                    System.out.println(time);
                    timeTextView.setText(Long.toString(time));
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = QueryBuilder.select(SelectResult.expression(Meta.id))
                        .from(DataSource.database(database));

                try {
                    List<Result> results = query.execute().allResults();

                    start = System.currentTimeMillis();
                    for(int i = 0; i < results.size(); ++i) {
                        MutableDocument mutDoc = database.getDocument(results.get(i)
                                .getString("id")).toMutable();
                        mutDoc.setString("FirstName","Anna");
                        database.save(mutDoc);
                        //Document doc = database.getDocument(results.get(i).getString("id"));
                        //Log.d("CouchbaseLite Database:", doc.getString("FirstName"));
                    }
                    time = System.currentTimeMillis()-start;
                    System.out.println(time);
                    timeTextView.setText(Long.toString(time));
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start = System.currentTimeMillis();
                Query query = QueryBuilder.select(SelectResult.expression(Meta.id))
                        .from(DataSource.database(database))
                        .where(Expression.property("FirstName").equalTo(Expression.string("Anna")));

                try {
                    List<Result> results = query.execute().allResults();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
                time = System.currentTimeMillis()-start;
                System.out.println(time);
                timeTextView.setText(Long.toString(time));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            database.delete();
        } catch (CouchbaseLiteException e) {
            Log.d("CouchbaseLite Database:", "deleted");
            e.printStackTrace();
        }
    }
}
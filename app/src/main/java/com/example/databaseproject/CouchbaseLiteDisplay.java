package com.example.databaseproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.Meta;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.SelectResult;
import java.util.ArrayList;
import java.util.List;

public class CouchbaseLiteDisplay extends AppCompatActivity {

    private Database database;
    private Button refreshViewButton;
    private Button prevActivityButton;
    private ListView itemsListView;
    private List<String> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couchbase_lite_display);

        // Get the database (and create it if it doesn’t exist).
        DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
        config.setDirectory(String.format("%s/%s", getApplicationContext().getFilesDir(),"BazaCouch"));
        database = null;
        try {
            database = new Database("mydb", config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        refreshViewButton = (Button) findViewById(R.id.refreshViewButton2);
        prevActivityButton = (Button) findViewById(R.id.prevActivityButton2);
        itemsListView = (ListView) findViewById(R.id.itemsListView2);

        refreshViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = QueryBuilder.select(SelectResult.expression(Meta.id))
                        .from(DataSource.database(database));

                try {
                    List<Result> results = query.execute().allResults();
                    dataList = new ArrayList<>();

                    for(int i = 0; i < results.size(); ++i) {
                        Document doc = database.getDocument(results.get(i).getString("id"));
                        Log.d("Doc=",doc.toString());
                        dataList.add(
                            "Id=" + doc.getInt("Id") + ", " +
                            "FirstName=" + doc.getString("FirstName") + ", " +
                            "LastName=" + doc.getString("LastName") + ", " +
                            "Age=" + doc.getInt("Age") + ", " +
                            "DogName=" + doc.getString("DogName") + ", " +
                            "DogAge=" + doc.getInt("DogAge") + ", " +
                            "DogColor=" + doc.getString("DogColor")
                        );
                    }
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(CouchbaseLiteDisplay.this,
                                android.R.layout.simple_list_item_1
                                , dataList);
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

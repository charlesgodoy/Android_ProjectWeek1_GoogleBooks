package com.example.caz.bookapi_by_caz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


// Caz Godoy
// Project Week 1 - Google Book API
// Reaching minimum requirements with a a few extras
// Added second shelf

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BookListListener, AdapterView.OnItemClickListener {

    EditText editTextSearchQuery;
    ListView listViewSearchResults;
    Button buttonSearch;
    List<Book> bookResults;
    ArrayAdapter<String> arrayAdapter;
    String[] names;


    Button buttonMyShelves;
    BookDatabase bookDataBase;

    Button buttonSecondList;
    BookDatabaseSecond bookDataBaseSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        editTextSearchQuery = findViewById(R.id.editview_search_query);
        listViewSearchResults = findViewById(R.id.listview_search_results);
        buttonSearch = findViewById(R.id.button_search);

        // trying out implements view.onclicklistener
        buttonSearch.setOnClickListener(this);

        buttonMyShelves = findViewById(R.id.button_my_shelf);
        buttonMyShelves.setOnClickListener(this);

        buttonSecondList = findViewById(R.id.button_second_list);
        buttonSecondList.setOnClickListener(this);


        listViewSearchResults.setOnItemClickListener(this);
        bookDataBase = new BookDatabase(getApplicationContext());
        bookDataBase.initializeDatabase();

        bookDataBaseSecond = new BookDatabaseSecond(getApplicationContext());
        bookDataBaseSecond.initializeDatabase();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == buttonSearch.getId()){
            // for search request

            String query = editTextSearchQuery.getText().toString();
            Toast.makeText(this, editTextSearchQuery.getText().toString(), Toast.LENGTH_SHORT).show();
            // async task
            BookSearchTask task = new BookSearchTask(this);

            task.execute(query);
        } else if(view.getId() == buttonMyShelves.getId()){

            // shelf intent
            Intent intent = new Intent(MainActivity.this, ShelfListActivity.class);
            startActivity(intent);

        } else if(view.getId() == buttonSecondList.getId()) {

            // 2nd shelf intent
            Intent intentSecond = new Intent(MainActivity.this, ShelfSecond.class);
            startActivity(intentSecond);
        }
    }

    @Override
    public void onBooksLoaded(List<Book> bookList) {

        bookResults = bookList;

        names = new String[bookList.size()];
        for(int i = 0; i < bookList.size(); ++i){
            names[i] = bookList.get(i).getTitle();
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, names);

        listViewSearchResults.setAdapter(arrayAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // send book to next activity

        Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
        intent.putExtra("selectedBook", this.bookResults.get(i));
        startActivity(intent);
    }
}
package com.example.caz.bookapi_by_caz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookShelfActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> names;
    ArrayAdapter<String> arrayAdapter;
    ListView listViewShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        listViewShelf = findViewById(R.id.listview_shelf);
        listViewShelf.setOnItemClickListener(this);



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String shelfname = names.get(position);

        Intent intent = new Intent(BookShelfActivity.this, ShelfListActivity.class);
        intent.putExtra("shelfname", shelfname);
        startActivity(intent);
    }
}

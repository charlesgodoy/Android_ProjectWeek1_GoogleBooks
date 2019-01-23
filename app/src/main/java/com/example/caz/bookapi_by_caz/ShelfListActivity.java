package com.example.caz.bookapi_by_caz;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShelfListActivity extends AppCompatActivity {


    ArrayList<String> names;
    ArrayAdapter<String> arrayAdapter;
    ListView listViewShelf;
    String selectedShelf;
    ArrayList<String> bookList;
    BookDatabase bookDataBase;

    Context context;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_list);

        // shelf name from previous activity
        context = this;
        listViewShelf = findViewById(R.id.listview_shelf_books);
        bookDataBase = new BookDatabase(getApplicationContext());
        listViewShelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getApplicationContext(), bookList.get(position).toString(), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(context).setMessage("Delete: "+ bookList.get(position)).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookDataBase.removeBook(books.get(position).getIsbn());
                        Toast.makeText(context, "Book Deleted!", Toast.LENGTH_SHORT).show();
                        loadBooks();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        loadBooks();
    }

    private void loadBooks() {

        books = bookDataBase.getReadBooks();
        bookList = new ArrayList<>();

        for (Book b : books) {
            bookList.add(b.getTitle());
        }

        String names[] = bookList.toArray(new String[bookList.size()]);
        arrayAdapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, names) {

            // change to black this way cause color is showing faded for some reason
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        listViewShelf.setAdapter(arrayAdapter);

    }

}
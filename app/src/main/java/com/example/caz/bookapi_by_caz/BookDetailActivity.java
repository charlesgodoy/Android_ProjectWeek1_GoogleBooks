package com.example.caz.bookapi_by_caz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Book selectedBook;
    TextView textViewBookTitle;
    ImageView imageViewBookImage;
    TextView textViewBookIsbn;
    TextView textViewBookDesc;
    TextView textViewBookPublisher;
    TextView textViewBookYear;

    Button buttonMarkRead;
    BookDatabase db;

    Button buttonToRead;
    BookDatabaseSecond dbSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();
        selectedBook = (Book)intent.getSerializableExtra("selectedBook");

        imageViewBookImage = findViewById(R.id.imageview_book_image);
        textViewBookIsbn = findViewById(R.id.textview_book_isbn);
        textViewBookTitle = findViewById(R.id.textview_book_title);
        textViewBookDesc = findViewById(R.id.textview_book_desc);
        textViewBookYear = findViewById(R.id.textview_book_year);
        textViewBookPublisher = findViewById(R.id.textview_book_publisher);

        textViewBookTitle.setText(selectedBook.getTitle());
        textViewBookIsbn.setText(selectedBook.getIsbn());

        try{

            textViewBookDesc.setText(selectedBook.getDescription().substring(0, 255)+"...");

        } catch (StringIndexOutOfBoundsException e){
            textViewBookDesc.setText("Description too long!");
        }

        textViewBookPublisher.setText(selectedBook.getPublisher());
        textViewBookYear.setText(selectedBook.getYear());
        GetImageFromUrl task = new GetImageFromUrl(imageViewBookImage);
        task.execute(selectedBook.getImageLink());

        buttonMarkRead = findViewById(R.id.button_mark_as_read);
        buttonMarkRead.setOnClickListener(this);

        buttonToRead = findViewById(R.id.button_to_read);
        buttonToRead.setOnClickListener(this);

        // access bookDataBaseSecond
        db = new BookDatabase(getApplicationContext());
        dbSecond = new BookDatabaseSecond(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonMarkRead.getId()) {


            selectedBook.setRead(true);


            db.markBookAsRead(selectedBook);

        } else if(view.getId() == buttonToRead.getId()) {
            selectedBook.setToRead(true);
            dbSecond.markBookToRead(selectedBook);
        }
    }
}
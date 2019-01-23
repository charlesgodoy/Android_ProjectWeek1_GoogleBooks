package com.example.caz.bookapi_by_caz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookDatabaseSecond {
    private Context context;
    private SQLiteDatabase database;

    public BookDatabaseSecond(Context context) {

        this.context = context;
    }

    public void initializeDatabase(){

        database = context.openOrCreateDatabase("app_db_second", Context.MODE_PRIVATE, null);
        // create the table with fields and data types

        // youtube vid tutorial suggest to look up Room Database Persistence library by google
        database.execSQL("CREATE TABLE IF NOT EXISTS BOOK" +
                "(isbn varchar, title varchar, description varchar, publisher varchar, year varchar, selfLink varchar, imageLink varchar, review varchar, read boolean, favorite boolean)");
        database.close();
    }

    public void markBookToRead(Book book){
        if(checkReadExists(book)){
            Toast.makeText(context, "Book already exists!", Toast.LENGTH_SHORT).show();
            return;
        }
        // get bookDataBaseSecond reference
        database = context.openOrCreateDatabase("app_db_second", Context.MODE_PRIVATE, null);

        // always a hassle working on these quotes
        book.setDescription(book.getDescription().replace("'", "_"));
        book.setPublisher(book.getPublisher().replace("'", "_"));
        book.setTitle(book.getTitle().replace("'","_"));
        database.execSQL("INSERT into BOOK VALUES ("+
                "'"+book.getIsbn()+"'," +
                "'"+book.getTitle()+"'," +
                "'"+book.getDescription()+"'," +
                "'"+book.getPublisher()+"'," +
                "'"+book.getYear()+"'," +
                "'" +book.getSelfLink()+"',"+
                "'" +book.getImageLink()+"',"+
                "'" +book.getReview()+"',"+
                "'" +book.isToRead()+"',"+
                "'"+false+"');");
        database.close();
        Toast.makeText(context, "Added to read list!", Toast.LENGTH_SHORT).show();
    }

    private boolean checkReadExists(Book book){
        database = context.openOrCreateDatabase("app_db_second", Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM BOOK WHERE read = 'true' and isbn = '"+book.getIsbn()+"'",null);
        int count = cursor.getCount();
        database.close();
        if(count > 0){
            // book already exists
            return true;
        }
        return false;

    }

    public List<Book> getToReadBooks(){

        List<Book> bookList = new ArrayList<>();
        database = context.openOrCreateDatabase("app_db_second", Context.MODE_PRIVATE, null);


        // cursor points to things in database
        Cursor cursor = database.rawQuery("SELECT * FROM BOOK WHERE read = 'true'",null);
        // check fetch records so we can iterate over them
        int recordsSize = cursor.getCount();

        if(recordsSize == 0) {
            // no records, return empty list
            return bookList;
        }
        cursor.moveToFirst(); // first to the first record
        for(int i=0; i<recordsSize; ++i){
            Book book = new Book();
            book.setIsbn(cursor.getString(0)); // indexing starts from column 1
            book.setTitle(cursor.getString(1));
            book.setDescription(cursor.getString(2));
            book.setPublisher(cursor.getString(3));
            book.setYear(cursor.getString(4));
            book.setSelfLink(cursor.getString(5));
            book.setImageLink(cursor.getString(6));
            book.setReview(cursor.getString(7));
            book.setToRead(Boolean.parseBoolean(cursor.getString(8)));

            // create book from cursor and add to list
            bookList.add(book);

            // then point to next record
            cursor.moveToNext();
        }
        database.close();
        return bookList;
    }

    public void removeBook(String isbn){
        database = context.openOrCreateDatabase("app_db_second", Context.MODE_PRIVATE, null);
        database.execSQL("DELETE FROM BOOK where isbn = '" + isbn + "';");
        database.close();
        return;
    }

}

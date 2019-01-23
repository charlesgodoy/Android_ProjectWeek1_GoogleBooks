package com.example.caz.bookapi_by_caz;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookSearchTask extends AsyncTask<String, String, List<Book>> {

    private BookListListener bookListListener;

    public BookSearchTask(BookListListener bookListListener) {
        this.bookListListener = bookListListener;
    }

    private String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";


    @Override
    protected List<Book> doInBackground(String... strings) {

        String query = strings[0];

        query = query.replace(" ", "+");// Make the query suitable to be used in url
        String requestUrl = BASE_URL + query;

        // have a valid url in requestUrl
        Log.d("URL", requestUrl);
        String response =  NetworkAdapter.httpRequest(requestUrl);

        List<Book> bookList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray items = jsonObject.getJSONArray("items");
            // get the json and try to make book object
            for(int i=0; i<items.length(); ++i){
                JSONObject bookJson = items.getJSONObject(i);


                Book book = new Book(bookJson);

                bookList.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return bookList;

    }

    @Override
    protected void onPostExecute(List<Book> bookList) {
        super.onPostExecute(bookList);
        Log.d("Book List", bookList.toString());


        bookListListener.onBooksLoaded(bookList);
    }
}
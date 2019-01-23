package com.example.caz.bookapi_by_caz;

import java.util.List;

public interface BookListListener {
    public void onBooksLoaded(List<Book> bookList);
}

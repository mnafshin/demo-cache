package com.example.democache.service;

import java.util.List;

import com.example.democache.persistence.Book;

public interface BookService {
    List<Book> findBooks();

    Book retrieveBook(long id);
}

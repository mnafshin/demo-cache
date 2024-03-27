package com.example.democache.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.democache.persistence.Book;
import com.example.democache.service.BookService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    List<Book> books = List.of(
            new Book(1, "mybook", "me"),
            new Book(2, "yourbook", "you")
    );

    @Override
    public List<Book> findBooks() {
        log.info("BookServiceImpl.findBooks()");
        return books;
    }

    @Cacheable(value = "book", key = "#id")
    @Override
    public Book retrieveBook(long id) {
        log.info("BookServiceImpl.retrieveBook(" + id + ")");
        var book = books.stream().filter(b -> Objects.equals(b.getId(), id)).findFirst();
        return book.orElse(null);
    }


}

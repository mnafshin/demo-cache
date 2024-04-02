package com.example.democache.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
        return findBook(id);
    }

    @CachePut(value = "book", key = "#id")
    @Override
    public Book updateBook(long id, String name, String author) {
        var book = findBook(id);
        if (book != null) {
            if (!name.isEmpty()) book.setName(name);
            if (!author.isEmpty()) book.setAuthor(author);
            return book;
        }
        return null;
    }

    @CacheEvict(value = "book", key = "#id")
    @Override
    public void invalidateBookCache(long id) {
        log.info("The cache for book {} is invalidated", id);
    }

    @CacheEvict(value = "book", allEntries = true)
    @Override
    public void invalidateAllBookCaches() {
        log.info("The cache for all books are invalidated");
    }

    private Book findBook(long id) {
        return books.stream().filter(b -> Objects.equals(b.getId(), id)).findFirst().orElse(null);
    }


}

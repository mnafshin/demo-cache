package com.example.democache.control;

import java.util.List;

import javax.money.MonetaryAmount;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.democache.persistence.Book;
import com.example.democache.service.BookPrice;
import com.example.democache.service.BookService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Api {

    private final BookService bookService;
    private final BookPrice bookPrice;

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/books")
    public List<Book> retrieveBooks() {
        return bookService.findBooks();
    }

    @GetMapping("/book/{id}")
    public Book retrieveBook(@PathVariable long id) {
        return bookService.retrieveBook(id);
    }

    @GetMapping("/book/{id}/price")
    public Long retrieveBookPriceLong(@PathVariable long id) {
        return bookPrice.getLongPrice(bookService.retrieveBook(id));
    }

    @GetMapping("/book/{id}/price2")
    public MonetaryAmount retrieveBookPrice2(@PathVariable long id) {
        return bookPrice.getMonetaryAmountPrice(bookService.retrieveBook(id));
    }

    @PutMapping("/book/{id}/update")
    public Book updateBook(@PathVariable long id,
                           @RequestParam(name = "name", defaultValue = "") String name,
                           @RequestParam(name = "author", defaultValue = "") String author) {

        return bookService.updateBook(id, name, author);
    }

    @PostMapping("/book/{id}/invalidateCache")
    public void invalidateBookCache(@PathVariable long id) {
        bookService.invalidateBookCache(id);
    }

    @PostMapping("/books/invalidateCache")
    public void invalidateAllBooksCache() {
        bookService.invalidateAllBookCaches();
    }
}

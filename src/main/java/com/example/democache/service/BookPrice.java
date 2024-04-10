package com.example.democache.service;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.democache.persistence.Book;

@Service
public class BookPrice {

    @Cacheable(cacheNames = "bookPrice", key = "#book.id")
    public Long getLongPrice(Book book) {
        return 100 * book.getId();
    }

    @Cacheable(cacheNames = "bookPrice2", key = "#book.id")
    public MonetaryAmount getMonetaryAmountPrice(Book book) {
        return Money.of(100 * book.getId(), "USD");
    }
}

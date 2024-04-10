package com.example.democache.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    private Long id;
    private String name;
    private String author;
}

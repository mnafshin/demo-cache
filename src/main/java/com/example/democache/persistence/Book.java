package com.example.democache.persistence;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book implements Serializable {
    private long id;
    private String name;
    private String author;
}

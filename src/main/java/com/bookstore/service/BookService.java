package com.bookstore.service;

import com.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}

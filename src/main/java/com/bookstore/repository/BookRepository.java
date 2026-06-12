package com.bookstore.repository;

import com.bookstore.entity.Book;

import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}

package com.bookstore.service;

import com.bookstore.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);
}

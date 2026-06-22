package com.bookstore;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            // Якщо в базі взагалі немає книг — додаємо початкові дані
            if (bookService.findAll().isEmpty()) {
                Book cleanCode = new Book();
                cleanCode.setTitle("Clean Code");
                cleanCode.setAuthor("Robert Martin");
                cleanCode.setIsbn("978-0132350884");
                cleanCode.setPrice(BigDecimal.valueOf(999.00));

                bookService.save(cleanCode);
            }
        };
    }
}

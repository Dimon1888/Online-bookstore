package com.bookstore;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import java.math.BigDecimal;
import java.util.Optional;
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
            if (bookService.findAll().isEmpty()) {
                Book cleanCode = new Book();
                cleanCode.setTitle("Clean Code");
                cleanCode.setAuthor("Robert Martin");
                cleanCode.setIsbn("978-0132350884");
                cleanCode.setPrice(BigDecimal.valueOf(999.00));

                bookService.save(cleanCode);
            }
            System.out.println("\nCurrent books in the database:");
            bookService.findAll().forEach(System.out::println);

            Book firstBook = bookService.findAll().get(0);
            Long bookId = firstBook.getId();

            System.out.println("\n2. Testing the search for ID = " + bookId);
            Optional<Book> foundBookOpt = bookService.findById(bookId);

            if (foundBookOpt.isPresent()) {
                Book foundBook = foundBookOpt.get();
                System.out.println("Book found: " + foundBook.getTitle());

                System.out.println("\n3. Testing price updates for books...");
                foundBook.setPrice(BigDecimal.valueOf(1250.50));

                bookService.save(foundBook);
                System.out.println("Price updated successfully! Verification: "
                        + bookService.findById(bookId).get());
            } else {
                System.out.println("A book from ID " + bookId + " not found.");
            }

            System.out.println("\n--- TESTING COMPLETED ---");
        };
    }
}


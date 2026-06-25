package com.bookstore.repository;

import com.bookstore.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book save(Book book) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Book savedBook;
            // Перевіряємо, чи книга вже існує в базі даних
            if (book.getId() == null) {
                // Якщо ID немає — це нова книга, створюємо новий рядок (INSERT)
                entityManager.persist(book);
                savedBook = book;
            } else {
                // Якщо ID є — книга існуюча, оновлюємо дані у базі (UPDATE)
                savedBook = entityManager.merge(book);
            }

            transaction.commit();
            return savedBook; // Повертаємо керовану JPA копію об'єкта
        } catch (RuntimeException e) {
            // Безпечний відкат транзакції з перевіркою активності з'єднання
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    // Приглушаємо помилку відкату, щоб не втратити оригінальний Exception
                    e.addSuppressed(rollbackEx);
                }
            }
            throw e;
        }
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Book book = entityManager.find(Book.class, id);
            return Optional.ofNullable(book);
        }
    }
}


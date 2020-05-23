package me.bang.springboot2test.repository;

import me.bang.springboot2test.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}

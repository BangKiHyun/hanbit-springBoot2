package me.bang.springboot2test.service;

import me.bang.springboot2test.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getBookList();
}

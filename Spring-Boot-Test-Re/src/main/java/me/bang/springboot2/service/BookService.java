package me.bang.springboot2.service;

import me.bang.springboot2.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getBookList();
}

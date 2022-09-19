package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.Set;

public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    Set<Long> getBooksByUserId(Long id);
}

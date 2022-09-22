package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    Storage<BookEntity> booksStorage;
    BookMapper bookMapper;

    public BookServiceImpl(Storage<BookEntity> booksStorage, BookMapper bookMapper) {
        this.booksStorage = booksStorage;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(Long parentId, BookDto bookDto) {
        bookDto.setUserId(parentId);
        Long id = booksStorage.add(bookMapper.bookDtoToBookEntity(bookDto));
        bookDto.setId(id);
        log.info("book created, book id assigned, book saved");
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        BookEntity entity = bookMapper.bookDtoToBookEntity(bookDto);
        booksStorage.save(entity);
        log.info("book updated, book saved");
        return bookMapper.bookEntityToBookDto(entity);
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info(String.format("getting book with id - %d", id));
        Optional<BookEntity> optionalBookEntity = booksStorage.get(id);
        if (optionalBookEntity.isEmpty())
            throw new BookNotFoundException(String.format("there is no book with id - %d", id));
        return bookMapper.bookEntityToBookDto(optionalBookEntity.get());
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("deleting book with id - {}", id);
        booksStorage.delete(id);
    }

    @Override
    public Set<Long> getBooksByUserId(Long id) {
        log.info("getting books from user with id - {}", id);
        return booksStorage.getChildList(id);
    }

}

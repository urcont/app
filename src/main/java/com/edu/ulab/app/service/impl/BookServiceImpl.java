package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.Entity;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageIdDataMap;
import com.edu.ulab.app.storage.StorageMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    Storage booksStorage = new Storage(new StorageMap(BookEntity.class.getName(), new StorageIdDataMap()));

    @Override
    public BookDto createBook(BookDto bookDto) {
        Entity entity = new BookEntity();
        entity.getEntityFromDto(bookDto);
        Long id = booksStorage.add(entity);
        bookDto.setId(id);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Entity entity = booksStorage.get(bookDto.getId());
        entity.getEntityFromDto(bookDto);
        booksStorage.save(entity);
        return (BookDto) entity.getDtoFromEntity();
    }

    @Override
    public BookDto getBookById(Long id) {
        return (BookDto) booksStorage.get(id).getDtoFromEntity();
    }

    @Override
    public void deleteBookById(Long id) {
        booksStorage.delete(id);
    }

    @Override
    public Set<Long> getBooksByUserId(Long id) {
        Set<Long> res = booksStorage.getChildList(id);
        if (res == null) {
            return new HashSet<>();
        }
        return booksStorage.getChildList(id);
    }

}

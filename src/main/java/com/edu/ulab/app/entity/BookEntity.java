package com.edu.ulab.app.entity;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BookEntity extends Entity {
    private String title;
    private String author;
    private long pageCount;

    @Override
    public void getEntityFromDto(Object object) {
        BookDto bookDto;
        if (object instanceof BookDto) {
            bookDto = (BookDto) object;
        } else {
            throw new NotFoundException("book casting Error");
        }

        setId(bookDto.getId());
        setParentId(bookDto.getUserId());
        setTitle(bookDto.getTitle());
        setAuthor(bookDto.getAuthor());
        setPageCount(bookDto.getPageCount());
    }

    @Override
    public Object getDtoFromEntity() {
        BookDto bookDto = new BookDto();
        bookDto.setId(getId());
        bookDto.setUserId(getParentId());
        bookDto.setTitle(getTitle());
        bookDto.setAuthor(getAuthor());
        bookDto.setPageCount(getPageCount());
        return bookDto;
    }
}

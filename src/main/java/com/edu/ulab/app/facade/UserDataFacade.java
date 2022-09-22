package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Entity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    /**
     * The simple logic is to create any user and their books no matter if there is an id in the request or not
     */
    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);
        final Long parentId = userDto.getId();
        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(mappedBookDto -> log.info("Mapped book: {}", mappedBookDto))
                .map(bookDto -> bookService.createBook(parentId, bookDto))
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    /**
     * The simple logic is to update user with current id. If user doesn't exist throws exception.
     * Any book from request will be added.
     */
    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book update request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto updatedUser = userService.updateUser(userDto);
        log.info("Updated user: {}", updatedUser);
        final Long parentId = updatedUser.getId();
        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(mappedBookDto -> log.info("Mapped book: {}", mappedBookDto))
                .map(bookDto -> bookService.createBook(parentId, bookDto))
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(updatedUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    /**
     * Returns userId + bookId list
     */
    public UserBookResponse getUserWithBooks(Long userId) {
        return UserBookResponse.builder()
                .userId(userService.getUserById(userId).getId())
                .booksIdList(bookService.getBooksByUserId(userId).stream().toList())
                .build();
    }

    /**
     * Deletes user and their books
     */
    public void deleteUserWithBooks(Long userId) {
        bookService.getBooksByUserId(userId).
                stream()
                .filter(Objects::nonNull)
                .forEach(bookId -> {
                    log.info("Deleting book: {}", bookId);
                    bookService.deleteBookById(bookId);
                    log.info("Book has been deleted: {}", bookId);
                });

        log.info("Deleting user: {}", userId);
        userService.deleteUserById(userId);
        log.info("User has been deleted: {}", userId);
    }
}

package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    Storage<UserEntity> usersStorage;
    UserMapper userMapper;

    public UserServiceImpl(Storage<UserEntity> usersStorage, UserMapper userMapper) {
        this.usersStorage = usersStorage;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Long id = usersStorage.add(userMapper.userDtoToUserEntity(userDto));
        userDto.setId(id);
        log.info("user created, user id assigned, user saved");
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity entity = userMapper.userDtoToUserEntity(userDto);
        usersStorage.save(entity);
        log.info("user updated, user saved");
        return userMapper.userEntityToUserDto(entity);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("getting user with id - {}", id);
        Optional<UserEntity> optionalUserEntity = usersStorage.get(id);
        if (optionalUserEntity.isEmpty())
            throw new UserNotFoundException(String.format("there is no user with id - %d", id));
        return userMapper.userEntityToUserDto(optionalUserEntity.get());
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("deleting user with id - {}", id);
        usersStorage.delete(id);
    }
}

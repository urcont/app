package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Entity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageIdDataMap;
import com.edu.ulab.app.storage.StorageMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    Storage usersStorage = new Storage(new StorageMap(UserEntity.class.getName(), new StorageIdDataMap()));

    @Override
    public UserDto createUser(UserDto userDto) {
        Entity entity = new UserEntity();
        entity.getEntityFromDto(userDto);
        Long id = usersStorage.add(entity);
        userDto.setId(id);

        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Entity entity = checkGetEntity(userDto.getId());
        entity.getEntityFromDto(userDto);
        usersStorage.save(entity);
        return (UserDto) entity.getDtoFromEntity();
    }

    @Override
    public UserDto getUserById(Long id) {
        return (UserDto) checkGetEntity(id).getDtoFromEntity();
    }

    @Override
    public void deleteUserById(Long id) {
        checkGetEntity(id);
        usersStorage.delete(id);
    }

    private Entity checkGetEntity(Long id) {
        Entity entity = usersStorage.get(id);
        if (entity == null) {
            throw new NotFoundException(String.format("There is no user with id: %d", id));
        }
        return entity;
    }
}

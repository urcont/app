package com.edu.ulab.app.entity;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends Entity {
    private String fullName;
    private String title;
    private int age;

    @Override
    public void getEntityFromDto(Object object) {
        UserDto userDto;
        if (object instanceof UserDto) {
            userDto = (UserDto) object;
        } else {
            throw new NotFoundException("user casting Error");
        }

        setId(userDto.getId());
        setFullName(userDto.getFullName());
        setTitle(userDto.getTitle());
        setAge(userDto.getAge());
    }

    @Override
    public Object getDtoFromEntity() {
        UserDto userDto = new UserDto();
        userDto.setId(getId());
        userDto.setFullName(getFullName());
        userDto.setTitle(getTitle());
        userDto.setAge(getAge());
        return userDto;
    }
}
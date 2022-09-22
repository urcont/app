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
}
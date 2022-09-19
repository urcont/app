package com.edu.ulab.app.entity;

import lombok.Data;

@Data
public abstract class Entity {
    private Long id;
    private Long parentId;

    public abstract void getEntityFromDto(Object object);

    public abstract Object getDtoFromEntity();
}

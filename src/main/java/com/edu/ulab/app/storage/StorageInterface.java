package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Entity;
import java.util.Set;

public interface StorageInterface {
    Long add(Entity entity);
    void save(Entity entity);
    Entity get(Long id);
    Set<Long> getChildList(Long id);
    void delete(Long id);
}

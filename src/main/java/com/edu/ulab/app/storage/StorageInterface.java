package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Entity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface StorageInterface<T extends Entity> {
    Long add(T entity);
    void save(T entity);
    Optional<T> get(Long id);
    Set<Long> getChildList(Long id);
    void delete(Long id);
}

package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Entity;
import com.edu.ulab.app.exception.NotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Scope("prototype")
public class Storage<T extends Entity> implements StorageInterface<T> {
    StorageIdData storageIdData;
    private final Map<Long, T> entityMap;
    private final Map<Long, Set<Long>> childrenEntityMap;

    public Storage(StorageIdData storageIdData) {
        this.storageIdData = storageIdData;
        this.entityMap = new HashMap<>();
        this.childrenEntityMap = new HashMap<>();
    }

    @Override
    public Long add(T entity) {
        Long id = storageIdData.getNewId(entity.getClass().getName());
        entity.setId(id);
        save(entity);
        return id;
    }

    @Override
    public void save(T entity) {
        entityMap.put(entity.getId(), entity);
        saveChildConnection(entity);
    }

    @Override
    public Optional<T> get(Long id) {
        T res = entityMap.get(id);
        return Optional.ofNullable(res);
    }

    @Override
    public Set<Long> getChildList(Long id) {
        var res = childrenEntityMap.get(id);
        if(res == null)
            return new HashSet<>();
        return childrenEntityMap.get(id);
    }

    @Override
    public void delete(Long id) {
        entityMap.remove(id);
        childrenEntityMap.remove(id);
    }

    private void saveChildConnection(T entity) {
        Long parentId = entity.getUserId();
        if (parentId != null) {
            Set<Long> childrenSet = childrenEntityMap.get(parentId);
            if (childrenSet == null)
                childrenSet = new HashSet<>();
            childrenSet.add(entity.getId());
            childrenEntityMap.put(parentId, childrenSet);
        }
    }
}

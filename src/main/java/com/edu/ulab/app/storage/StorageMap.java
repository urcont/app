package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Entity;
import com.edu.ulab.app.exception.NotFoundException;

import java.util.*;

public class StorageMap implements StorageInterface {
    StorageIdData storageIdData;
    private final String clazz;
    private final Map<Long, Entity> entityMap;
    private final Map<Long, Set<Long>> childrenEntityMap;

    public StorageMap(String clazz, StorageIdData storageIdData) {
        this.clazz = clazz;
        this.storageIdData = storageIdData;
        this.entityMap = new HashMap<>();
        this.childrenEntityMap = new HashMap<>();
    }

    @Override
    public Long add(Entity entity) {
        Long id = storageIdData.getNewId(this.clazz);
        entity.setId(id);
        save(entity);
        return id;
    }

    @Override
    public void save(Entity entity) {
        entityMap.put(entity.getId(), entity);
        saveChildConnection(entity);
    }

    @Override
    public Entity get(Long id) {
        Entity res = entityMap.get(id);
        if(res == null)
            throw new NotFoundException(String.format("There is no element with id: %d", id));
        return entityMap.get(id);
    }

    @Override
    public Set<Long> getChildList(Long id) {
        return childrenEntityMap.get(id);
    }

    @Override
    public void delete(Long id) {
        entityMap.remove(id);
        childrenEntityMap.remove(id);
    }

    private void saveChildConnection(Entity entity) {
        Long parentId = entity.getParentId();
        if (parentId != null) {
            Set<Long> childrenSet = childrenEntityMap.get(parentId);
            if (childrenSet == null)
                childrenSet = new HashSet<>();
            childrenSet.add(entity.getId());
            childrenEntityMap.put(parentId, childrenSet);
        }
    }
}

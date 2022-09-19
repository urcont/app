package com.edu.ulab.app.storage;

import com.edu.ulab.app.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Some kind of unique id control implementation.
 */
public class StorageIdDataMap extends StorageIdData {
    private final Map<String, Long> idMap = new HashMap<>();
    @Override
    public synchronized Long getNewId(String clazz) {
        Long lastId = idMap.get(clazz);
        if(lastId == null) {
            lastId = 0L;
        } else
        if(lastId == Long.MAX_VALUE) {
            throw new NotFoundException(String.format("too many Objects: id = %d", lastId));
        }
        lastId++;
        idMap.put(clazz, lastId);
        return lastId;
    }
}

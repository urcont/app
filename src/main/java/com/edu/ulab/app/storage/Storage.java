package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Entity;

import java.util.Set;

public class Storage {
    StorageInterface storageObject;

    public Storage(StorageInterface storageObject) {
        this.storageObject = storageObject;
    }

    public Long add(Entity entity) {
        return storageObject.add(entity);
    }

    public void save(Entity entity) {
        storageObject.save(entity);
    }

    public Entity get(Long id) {
        return storageObject.get(id);
    }

    public Set<Long> getChildList(Long id) {
        return storageObject.getChildList(id);
    }

    public void delete(Long id) {
        storageObject.delete(id);
    }

    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции
}

package com.foxminded.university.dao;

import java.util.Optional;

public interface CrudDao<E, ID> {
    void save(E entity);

    Optional<E> findById(ID id);

    void update(E entity);

    void deleteById(ID id);

}

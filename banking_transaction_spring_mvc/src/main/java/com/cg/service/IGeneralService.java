package com.cg.service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    T getById(Long id);

    void save(T t);

    void remove(Long id);

    boolean existById(Long id);
}

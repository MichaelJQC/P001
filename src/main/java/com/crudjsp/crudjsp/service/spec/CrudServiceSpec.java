package com.crudjsp.crudjsp.service.spec;

import java.util.List;

public interface CrudServiceSpec<T> {

    List<T> getAllActive();
    List<T> getAllInactive();
    T getForId(int id);

    List<T> get(T bean);

    void insert(T bean);

    void update(T bean);

    void delete(int id);
    void restore(int id);
}
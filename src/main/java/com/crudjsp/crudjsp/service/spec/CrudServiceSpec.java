package com.crudjsp.crudjsp.service.spec;

import com.crudjsp.crudjsp.model.Producto;
import java.util.List;

public interface CrudServiceSpec<T> {

    List<T> getAll();
    T getForId(String id);

    List<T> get(T bean);

    void insert(T bean);

    void update(T bean);

    void delete(String id);

}
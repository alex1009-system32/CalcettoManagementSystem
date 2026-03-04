package org.example.calcettomanagmentsystem.dao;

import java.util.List;
import java.util.Optional;

public interface GeneralDao<T> {
        Optional<T> save(T obj);
        boolean delete(T obj);
        List<T> findAll();
        Optional<T> findById(int id);
}

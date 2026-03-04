package org.example.calcettomanagmentsystem.dao;

import java.util.List;

public interface GeneralDao<T> {
        T save(T obj);
        boolean delete(T obj);
        List<T> findAll();
        T findById(int id);
}

package org.example.calcettomanagmentsystem.service.interfaces;

import java.util.List;

public interface Repository<T> {
    T save(T obj);
    boolean delete(T obj);
    List<T> findAll();
    T findById(int id);
}

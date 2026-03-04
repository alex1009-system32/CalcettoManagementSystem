package org.example.calcettomanagmentsystem.service.interfaces;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> save(T obj);
    boolean delete(T obj);
    List<T> findAll();
    Optional<T> findById(int id);
}

package org.example.Distance.services;

import org.example.Distance.exception.BadRequestException;
import org.example.Distance.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService<T> {
    void create(T entity) throws BadRequestException;

    List<T> read();

    T getByName(String name) throws ResourceNotFoundException;

    T getByID(Long id) throws ResourceNotFoundException;

    void update(T entity) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
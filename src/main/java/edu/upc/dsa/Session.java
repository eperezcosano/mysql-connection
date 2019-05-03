package edu.upc.dsa;

import java.util.List;

public interface Session<E> {
    void save(Object entity);
    Object get(Class theClass, int id);
    void update(Object object, int id);
    void delete(Object object, int id);
    List<Object> findAll(Class theClass);
    void close() throws Exception;
}
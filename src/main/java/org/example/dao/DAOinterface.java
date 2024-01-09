package org.example.dao;

import org.example.model.AudioUS;

import java.sql.SQLException;

public interface DAOinterface<T> {
    public int insert(T t);
    public boolean update(T t);
    public boolean delete(T t);
    public T findbyID(int ID);

}

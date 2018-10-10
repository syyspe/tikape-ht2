/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author syyspe
 */
public interface Dao<T, K> {
    public int getCount() throws SQLException;
    public T findById(K key) throws SQLException;
    public List<T> findAll() throws SQLException;
    public T add(T object) throws SQLException;
    public T update(T object) throws SQLException;
    public void delete(K key) throws SQLException;
}

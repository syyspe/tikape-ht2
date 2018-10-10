/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.dao;
import ht2.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ht2.domain.AbstractHt2Object;

/**
 *
 * @author syyspe
 */
public abstract class AbstractHt2Dao<T extends AbstractHt2Object> implements Dao<T, Integer> {
    protected Database db;
    protected String tableName;

    public AbstractHt2Dao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }
    
    public abstract T createFromRow(ResultSet row) throws SQLException;

    @Override
    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
   
        try {
            conn = db.getConnection();
            System.out.println("TABLENAME: " + tableName);
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM " + tableName);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1);
            }
 
            return -1;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public T findById(Integer key) throws SQLException {
        T object = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?");
            stmt.setInt(1, key);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                object = createFromRow(rs);
            }
           
            return object;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    @Override
    public List<T> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try  {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM " + tableName);
            rs = stmt.executeQuery();
          
            List<T> objects = new ArrayList<>();
            
            while(rs.next()) {
                objects.add(createFromRow(rs));
            }
            
            return objects;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }    
    
}

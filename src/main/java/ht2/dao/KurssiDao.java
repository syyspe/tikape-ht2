/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.dao;

import ht2.database.Database;
import ht2.domain.Kurssi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author syyspe
 */
public class KurssiDao {
    private final Database db;

    public KurssiDao(Database db) {
        this.db = db;
    }
    
    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
   
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT() FROM Kurssi");
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
    
    public Kurssi findById(int id) throws SQLException {
        Kurssi kurssi = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Kurssi WHERE id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                kurssi = new Kurssi(rs.getInt("id"), rs.getString("nimi"), null);
            }
 
            return kurssi;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    
    public List<Kurssi> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Kurssi");
            rs = stmt.executeQuery();
            
            List<Kurssi> kurssit = new ArrayList<>();
             
            while(rs.next()) { 
                kurssit.add(new Kurssi(rs.getInt("id"), rs.getString("nimi"), null)); 
            }
            
            return kurssit;
           
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    
    public Kurssi add(Kurssi kurssi) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet id = null;
         
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO Kurssi (nimi) VALUES (?)", 
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, kurssi.getNimi());
            
            int rowsAffected = stmt.executeUpdate();
            id = stmt.getGeneratedKeys();
            if(rowsAffected != 1 || !id.next()) {
                throw new SQLException("Kurssin lisääminen epäonnistui");
            }
            return kurssi; 
        } finally {
            if (id != null) id.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }   
    }
    
    public void delete(int kurssiId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
         
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(
                    "DELETE FROM Kurssi WHERE id=?");
            stmt.setInt(1, kurssiId);
            
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected != 1) {
                throw new SQLException("Kurssin poistaminen epäonnistui");
            }
             
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }   
    }
    
}

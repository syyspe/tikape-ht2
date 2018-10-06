/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.dao;

import ht2.database.Database;
import ht2.domain.Kysymys;
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
public class KysymysDao {
    private final Database db;

    public KysymysDao(Database db) {
        this.db = db;
    }
    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
   
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT() FROM Kysymys");
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
    
    public Kysymys findById(int id) throws SQLException {
        Kysymys kysymys = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                kysymys = new Kysymys(
                        rs.getInt("id"), 
                        rs.getString("aihe"), 
                        rs.getString("teksti"),
                        rs.getInt("kurssi_id"),
                        null
                );
            }
           
            return kysymys;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    public List<Kysymys> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try  {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Kysymys");
            rs = stmt.executeQuery();
          
            List<Kysymys> kysymykset = new ArrayList<>();
            
            while(rs.next()) {
                kysymykset.add(new Kysymys(
                        rs.getInt("id"),
                        rs.getString("aihe"),
                        rs.getString("teksti"),
                        rs.getInt("kurssi_id"),
                        null));
            }
            
            return kysymykset;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    public List<Kysymys> findByCourseId(int kurssiId) throws SQLException {
         
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try  {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE kurssi_id=?");
            stmt.setInt(1, kurssiId);
            rs = stmt.executeQuery();
          
            List<Kysymys> kysymykset = new ArrayList<>();
            
            while(rs.next()) {
                kysymykset.add(new Kysymys(
                        rs.getInt("id"),
                        rs.getString("aihe"),
                        rs.getString("teksti"),
                        rs.getInt("kurssi_id"),
                        null));
            }
            
            return kysymykset;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    public Kysymys add(Kysymys kysymys) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet id = null;
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, kysymys.getAihe());
            stmt.setString(2, kysymys.getTeksti());
            stmt.setInt(3, kysymys.getKurssi_id());
            
            int rowsAffected = stmt.executeUpdate();
            id = stmt.getGeneratedKeys();
            if(rowsAffected != 1 || !id.next()) {
                throw new SQLException("Kysymyksen lisääminen epäonnistui");
            }
            kysymys.setId(id.getInt(rowsAffected));
            return kysymys;
        } finally {
            if(id != null) id.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
}

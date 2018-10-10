/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.dao;

import ht2.database.Database;
import ht2.domain.Kysymys;
import ht2.domain.Vastaus;
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
public class VastausDao extends AbstractHt2Dao<Vastaus> implements Dao<Vastaus, Integer>{
    private final Database db;

    public VastausDao(Database db) {
        super(db, "Vastaus");
        this.db = db;
    }

    @Override
    public Vastaus createFromRow(ResultSet row) throws SQLException {
        return new Vastaus(
                        row.getInt("id"), 
                        row.getString("teksti"), 
                        row.getBoolean("oikein"),
                        row.getInt("kysymys_id")
                );
    }
     /*
    @Override
    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
   
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM Vastaus");
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
    public Vastaus findById(Integer id) throws SQLException {
        Vastaus vastaus = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                vastaus = new Vastaus(
                        rs.getInt("id"), 
                        rs.getString("teksti"), 
                        rs.getBoolean("oikein"),
                        rs.getInt("kysymys_id")
                );
            }
           
            return vastaus;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    @Override
    public List<Vastaus> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try  {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Vastaus");
            rs = stmt.executeQuery();
          
            List<Vastaus> vastaukset = new ArrayList<>();
            
            while(rs.next()) {
                vastaukset.add(new Vastaus(
                        rs.getInt("id"), 
                        rs.getString("teksti"), 
                        rs.getBoolean("oikein"),
                        rs.getInt("kysymys_id")));
            }
            
            return vastaukset;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }*/
    
    public List<Vastaus> findByQuestionId(int kysymysId) throws SQLException {
         
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try  {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id=?");
            stmt.setInt(1, kysymysId);
            rs = stmt.executeQuery();
          
            List<Vastaus> vastaukset = new ArrayList<>();
            
            while(rs.next()) {
                vastaukset.add(new Vastaus(
                        rs.getInt("id"), 
                        rs.getString("teksti"), 
                        rs.getBoolean("oikein"),
                        rs.getInt("kysymys_id")));
            }
            
            return vastaukset;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    @Override
    public Vastaus add(Vastaus vastaus) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet id = null;
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, vastaus.getTeksti());
            stmt.setBoolean(2, vastaus.getOikein());
            stmt.setInt(3, vastaus.getKysymys_id());
            
            int rowsAffected = stmt.executeUpdate();
            id = stmt.getGeneratedKeys();
            if(rowsAffected != 1 || !id.next()) {
                throw new SQLException("Vastauksen lisääminen epäonnistui");
            }
            vastaus.setId(id.getInt(rowsAffected));
            return vastaus;
        } finally {
            if(id != null) id.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    @Override
    public void delete(Integer id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id=?");
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected != 1) {
                throw new SQLException("Vastauksen poistaminen epäonnistui");
            }
        
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public Vastaus update(Vastaus object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}

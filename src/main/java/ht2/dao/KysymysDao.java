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
public class KysymysDao extends AbstractHt2Dao<Kysymys> implements Dao<Kysymys, Integer> {
    private final Database db;

    public KysymysDao(Database db) {
        super(db, "Kysymys");
        this.db = db;
    }

    @Override
    public Kysymys createFromRow(ResultSet row) throws SQLException {
        return new Kysymys(
                        row.getInt("id"), 
                        row.getString("aihe"), 
                        row.getString("teksti"),
                        row.getInt("kurssi_id"),
                        null);
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
    
    public Kysymys add(Kysymys kysymys, List<Vastaus> vastaukset) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt_k = null, stmt_v = null;
        ResultSet id = null;
        
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
            stmt_k = conn.prepareStatement(
                    "INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt_k.setString(1, kysymys.getAihe());
            stmt_k.setString(2, kysymys.getTeksti());
            stmt_k.setInt(3, kysymys.getKurssi_id());
            
            int rowsAffected = stmt_k.executeUpdate();
            id = stmt_k.getGeneratedKeys();
            if(rowsAffected != 1 || !id.next()) {
                throw new SQLException("Kysymyksen lisääminen epäonnistui");
            }
            kysymys.setId(id.getInt(1));
            
            stmt_v = conn.prepareStatement(
                    "INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES (?,?,?)");
            for (Vastaus v : vastaukset) {
                stmt_v.setString(1, v.getTeksti());
                stmt_v.setBoolean(2, v.getOikein());
                stmt_v.setInt(3, kysymys.getId());
                rowsAffected = stmt_v.executeUpdate();
                if(rowsAffected != 1) {
                    throw new SQLException("Kysymyksen vastausten lisääminen epäonnistui");
                }   
            }
            kysymys.setVastaukset(vastaukset);
            conn.commit();
            id.close();
            stmt_k.close();
            stmt_v.close();
            conn.close();
            return kysymys;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            if(id != null) id.close();
            if(stmt_k != null) stmt_k.close();
            if(stmt_v != null) stmt_v.close();
            if(conn != null) conn.close();
            throw e;
        } 
    }
    
    @Override
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
            kysymys.setId(id.getInt(1));
            return kysymys;
        } finally {
            if(id != null) id.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    
    @Override
    public void delete(Integer kysymysId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtKysymys = null, stmtVastaus = null;
        ResultSet kysymykset = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            stmtKysymys = conn.prepareStatement(
                    "SELECT * FROM Kysymys WHERE id=?");
            stmtKysymys.setInt(1, kysymysId);
            kysymykset = stmtKysymys.executeQuery();
            if(!kysymykset.next()) {
                kysymykset.close();
                stmtKysymys.close();
                conn.close();
                return;
            }
            
            stmtVastaus = conn.prepareStatement(
                    "DELETE FROM Vastaus WHERE kysymys_id=?");
            stmtVastaus.setInt(1, kysymysId);
            int rowsAffected = stmtVastaus.executeUpdate();
            System.out.println("Poistettu " + rowsAffected + " vastausta");
            
            stmtKysymys.clearBatch();
            stmtKysymys = conn.prepareStatement(
                    "DELETE FROM Kysymys WHERE id=?");
            stmtKysymys.setInt(1, kysymysId);
            rowsAffected = stmtKysymys.executeUpdate();
            System.out.println("Poistettu " + rowsAffected + " kpl kysymyksiä");
            
            conn.commit();
            kysymykset.close();
            stmtKysymys.close();
            stmtVastaus.close();
            conn.close();
        } catch (SQLException e) {
            if(conn != null) conn.rollback();
            System.out.println("SQL: " + e.getMessage());
            if (kysymykset != null) kysymykset.close();
            if (stmtKysymys != null) stmtKysymys.close();
            if (stmtVastaus != null) stmtVastaus.close();
            if (conn != null) conn.close();
            throw e;
        }
    }

    @Override
    public Kysymys update(Kysymys object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
    
}

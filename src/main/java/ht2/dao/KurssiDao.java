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

/**
 *
 * @author syyspe
 */
public class KurssiDao extends AbstractHt2Dao<Kurssi> implements Dao<Kurssi, Integer>{
    private final Database db;

    public KurssiDao(Database db) {
        super(db, "Kurssi");
        this.db = db;
    }

    @Override
    public Kurssi createFromRow(ResultSet row) throws SQLException {
        return new Kurssi(row.getInt("id"), row.getString("nimi"), null);
    }

    @Override
    public String getOrdering() {
        return "ORDER BY id DESC";
    }
    
    
 
    @Override
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
    
    @Override
    public void delete(Integer kurssiId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtKurssi = null, stmtKysymys = null, stmtVastaus = null;
        ResultSet kurssit = null, kysymykset = null; 
        try {
            int rowsAffected = -1;
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
            stmtKurssi = conn.prepareStatement(
                    "SELECT * FROM Kurssi WHERE id=?");
            stmtKurssi.setInt(1, kurssiId);
            kurssit = stmtKurssi.executeQuery();
            if(!kurssit.next()) {
                kurssit.close();
                stmtKurssi.close();
                conn.close();
                return;
            }
            
            stmtKurssi.clearBatch();
            
            stmtKysymys = conn.prepareStatement("SELECT * from Kysymys WHERE kurssi_id =?");
            stmtKysymys.setInt(1, kurssiId);
            kysymykset = stmtKysymys.executeQuery();
            
            while (kysymykset.next()) {
                stmtVastaus = conn.prepareStatement("DELETE FROM Vastaus WHERE kysymys_id=?");
                stmtVastaus.setInt(1, kysymykset.getInt("id"));
                rowsAffected = stmtVastaus.executeUpdate();
                stmtVastaus.clearBatch();
            }
            
            stmtKysymys.clearBatch();
            stmtKysymys = conn.prepareStatement("DELETE FROM Kysymys WHERE kurssi_id=?");
            stmtKysymys.setInt(1, kurssiId);
            rowsAffected = stmtKysymys.executeUpdate();
            
            stmtKurssi = conn.prepareStatement(
                    "DELETE FROM Kurssi WHERE id=?");
            stmtKurssi.setInt(1, kurssiId);
            rowsAffected = stmtKurssi.executeUpdate();
            if(rowsAffected != 1) {
                throw new SQLException("Kurssin poistaminen epäonnistui");
            }
            
            conn.commit();             
            
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("SQL: " + e.getMessage());
            throw e;
        }  finally {
            if (kurssit != null) kurssit.close();
            if (kysymykset != null) kysymykset.close();
            if (stmtVastaus != null) stmtVastaus.close();
            if (stmtKysymys != null) stmtKysymys.close();
            if (stmtKurssi != null) stmtKurssi.close();
            if (conn != null) conn.close();
        } 
    }

    @Override
    public Kurssi update(Kurssi object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}

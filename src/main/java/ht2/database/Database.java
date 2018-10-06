/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.database;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author syyspe
 */
public class Database {
    private String address;

    public Database(String address) {
        this.address = System.getenv("JDBC_DATABASE_URL");
        if(this.address == null || this.address.length() == 0) {
            this.address = address;
        } 
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }
    
    public void initDb() throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Kurssi (id SERIAL PRIMARY KEY,nimi varchar(256) NOT NULL);");
        stmt.execute();
        stmt.clearBatch();
        
        System.out.println("initDb() Kurssi created");
        
        stmt = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Kysymys (id SERIAL PRIMARY KEY,aihe varchar(256),teksti varchar(1024) NOT NULL,kurssi_id integer NOT NULL,FOREIGN KEY(kurssi_id REFERENCES Kurssi(id));");
        stmt.execute();
        stmt.clearBatch();
        
        System.out.println("initDb() Kysymys created");
        
        stmt = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Vastaus (id SERIAL PRIMARY KEY,teksti varchar(1024) NOT NULL,oikein integer NOT NULL,kysymys_id integer NOT NULL,FOREIGN KEY(kysymys_id) REFERENCES Kysymys(id));");
        stmt.execute();
        stmt.clearBatch();
        
        System.out.println("initDb() Vastaus created");
        
        stmt.close();
        conn.close();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.database;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        } else {
            // Init the db if this is production env (Heroku)
            try {
                this.initDb();
                List<String> sql = new ArrayList<>();
                String dataFile = "init-psql.sql";
                String line = null;
                
                FileReader fr = new FileReader(dataFile);
                BufferedReader br = new BufferedReader(fr);
                
                while ((line = br.readLine()) != null) {
                    System.out.println("line: " + line);
                    sql.add(line);
                }
                
                this.addData(sql);
                
            } catch (SQLException e) {
                System.out.println("SQL: " + e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println("File: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            }
            
        }
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }
    
    private void initDb() throws SQLException {
        Connection conn = this.getConnection();
        
        PreparedStatement del3 = conn.prepareStatement("DROP TABLE Vastaus;");
        del3.execute();
        del3.close();
        
        PreparedStatement del2 = conn.prepareStatement("DROP TABLE Kysymys;");
        del2.execute();
        del2.close();
        
        PreparedStatement del1 = conn.prepareStatement("DROP TABLE Kurssi;");
        del1.execute();
        del1.close();
        
        PreparedStatement stmt1 = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Kurssi (id SERIAL PRIMARY KEY,nimi varchar(256) NOT NULL);");
        stmt1.execute();
        System.out.println("initDb() Kurssi created");
        
        PreparedStatement stmt2 = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Kysymys (id SERIAL PRIMARY KEY,aihe varchar(256),teksti varchar(1024) NOT NULL,kurssi_id integer NOT NULL,FOREIGN KEY(kurssi_id) REFERENCES Kurssi(id));");
        stmt2.execute();
        
        System.out.println("initDb() Kysymys created");
        
        PreparedStatement stmt3 = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Vastaus (id SERIAL PRIMARY KEY,teksti varchar(1024) NOT NULL,oikein integer NOT NULL,kysymys_id integer NOT NULL,FOREIGN KEY(kysymys_id) REFERENCES Kysymys(id));");
        stmt3.execute();
        
        System.out.println("initDb() Vastaus created");
        
        stmt1.close();
        stmt2.close();
        stmt3.close();
        conn.close();
    }
    
    private void addData(List<String> sql) throws SQLException {
        Connection conn = this.getConnection();
        for (String s : sql) {
            PreparedStatement stmt = conn.prepareStatement(s);
            stmt.execute();
            stmt.close();
        }
        conn.close();
    }
    
}

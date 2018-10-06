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
                    System.out.println("line: ");
                }
                
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
    
    private void addKurssiData() throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement stmt1, stmt2, stmt3, stmt4, stmt5 = null;
        
        stmt1 = conn.prepareStatement("INSERT INTO Kurssi (nimi) Values ('Johdatus tietojenkäsittelytieteeseen');");
        stmt1.execute();
        stmt1.close();
        
        stmt2 = conn.prepareStatement("INSERT INTO Kurssi (nimi) Values ('Ohjelmoinnin perusteet');");
        stmt2.execute();
        stmt2.close();
        
        stmt3 = conn.prepareStatement("INSERT INTO Kurssi (nimi) Values ('Ohjelmoinnin jatkokurssi');");
        stmt3.execute();
        stmt3.close();
        
        stmt4 = conn.prepareStatement("INSERT INTO Kurssi (nimi) Values ('Tietokantojen perusteet');");
        stmt4.execute();
        stmt4.close();
        
        stmt5 = conn.prepareStatement("INSERT INTO Kurssi (nimi) Values ('Full stack -websovelluskehitys');");
        stmt5.execute();
        stmt5.close();
        
        conn.close();
    }
    
    private void addKysymysData() throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement stmt1, stmt2,stmt3, stmt4, stmt5 = null;
        
        stmt1 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Kuuluisat henkilöt', 'Mistä Alan Turing tunnetaan?',1);");
        stmt1.execute();
        stmt1.close();
        
        stmt2 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 1', 'Minkä niminen metodi käynnstää Java-ohjelman?',2);");
        stmt2.execute();
        stmt2.close();
        
        stmt3 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 8', 'Miksi Java osaa kutsua toString() metodia mille tahansa oliolle?',3);");
        stmt3.execute();
        stmt3.close();
        
        stmt4 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Osa 3', 'Mihin SQL termiä AS voidaan käyttää?',4);");
        stmt4.execute();
        stmt4.close();
        
        stmt5 = conn.prepareStatement("");
        stmt5.execute();
        stmt5.close();
        
        conn.close();
    }
    
    private void addVastausData() throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement stmt1, stmt2,stmt3, stmt4, stmt5 = null;
        
        stmt1 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Kuuluisat henkilöt', 'Mistä Alan Turing tunnetaan?',1);");
        stmt1.execute();
        stmt1.close();
        
        stmt2 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 1', 'Minkä niminen metodi käynnstää Java-ohjelman?',2);");
        stmt2.execute();
        stmt2.close();
        
        stmt3 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 8', 'Miksi Java osaa kutsua toString() metodia mille tahansa oliolle?',3);");
        stmt3.execute();
        stmt3.close();
        
        stmt4 = conn.prepareStatement("INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Osa 3', 'Mihin SQL termiä AS voidaan käyttää?',4);");
        stmt4.execute();
        stmt4.close();
        
        stmt5 = conn.prepareStatement("");
        stmt5.execute();
        stmt5.close();
        
        conn.close();
    }
    
    
    
}

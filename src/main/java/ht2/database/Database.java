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
        
    private void initDb() throws SQLException, FileNotFoundException, IOException {
        FileReader fr = new FileReader("init-psql.sql");
        BufferedReader br = new BufferedReader(fr);
        
        Connection conn = this.getConnection();
        String line;
        
        while ((line = br.readLine()) != null) {
            if(line.isEmpty() || line.startsWith("--")) {
                continue;
            }
            System.out.print("initDb() - executing: " + line);
            conn.createStatement().execute(line);
            System.out.println(" ...OK");
        }
        
        conn.close();
    }
    
}

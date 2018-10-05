/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.database;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author syyspe
 */
public class Database {
    private String address;

    public Database(String address) {
        this.address = System.getenv("DATABASE_URL");
        if(this.address == null || this.address.length() == 0) {
            this.address = address;
        }
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }
    
}

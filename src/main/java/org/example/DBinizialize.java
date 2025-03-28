
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBinizialize {
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String pass = "root1701";
    private String db_name = "gestione_ordini_jpa";
    private Statement st;
    private Connection conn;
    private boolean dropDb = false;

    public DBinizialize(boolean dropDb) throws SQLException {
        this.dropDb = dropDb;
        this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
        this.st = this.conn.createStatement();
        System.out.println("DB Connesso!!");
        if (this.dropDb) {
            this.dropDatabase();
        }

        this.createDatabase();
    }

    public DBinizialize() throws SQLException {
        this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
        this.st = this.conn.createStatement();
        System.out.println("DB Connesso!!");
        this.createDatabase();
    }

    public void createDatabase() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + this.db_name;
        this.st.executeUpdate(sql);
        System.out.println("DB " + this.db_name + " e creato!!!");
        this.conn = DriverManager.getConnection(this.url + this.db_name, this.user, this.pass);
        this.st = this.conn.createStatement();
    }

    public void dropDatabase() throws SQLException {
        String sql = "DROP DATABASE IF EXISTS " + this.db_name;
        this.st.execute(sql);
        System.out.println("DB " + this.db_name + " e eliminato!!");
    }
}

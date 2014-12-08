package edu.ucsd.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Angie Nguyen on 12/5/2014.
 */
public class DatabaseConnect {

    String userName = "root";
    String password = "loop";

    String dbms ="mysql";
    //String serverName = "75.80.58.229";
    String serverName = "localhost";
    String portNumber ="3306";

    public static Connection conn =null;

    public DatabaseConnect () {
        try {
            this.conn = getConnection();
        }
        catch (Exception e) {
            System.out.println("Database connection error: " + e.toString());
        }
    }

    public Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbms.equals("mysql")) {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(
                    "jdbc:" + this.dbms + "://" +
                            this.serverName +
                            ":" + this.portNumber + "/",
                    connectionProps);
        }
        System.out.println("Connected to database");
        return conn;
    }
}

package edu.upc.dsa;

import org.apache.log4j.Logger;
import java.sql.*;

public class MysqlConn {

    final static Logger log = Logger.getLogger(MysqlConn.class.getName());

    public static Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection("jdbc:mysql://localhost/dsaDDBB", "root", "root");
        } catch (Exception e) {
            log.error("Error exception");
            e.printStackTrace();
            throw e;
        }
    }

    public static void getUsers(Connection conn) throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM User");
        while (rs.next()) log.info("ID: " + rs.getInt(1) + " Username: " + rs.getString(2) + " Password: " + rs.getString(3));
        stmt.close();
    }

    public static void insertUsers(Connection conn) throws Exception {
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO User (username, password) VALUES ('Maria','MMM')");
        stmt.close();
    }

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            insertUsers(conn);
            getUsers(conn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn!=null) conn.close();
        }
    }
}
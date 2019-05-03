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

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn!=null) conn.close();
        }
    }
}
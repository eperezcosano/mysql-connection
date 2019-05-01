import org.apache.log4j.Logger;
import java.sql.*;

public class MysqlConn {

    final static Logger log = Logger.getLogger(MysqlConn.class.getName());

    public static void main(String[] args) {

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dsaDDBB", "root", "root");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");

            while (rs.next()) log.info("ID: " + rs.getInt(1) +
                    " Username: " + rs.getString(2) +
                    " Password: " + rs.getString(3));

        } catch (Exception e) {
            log.error("Error exception");
            e.printStackTrace();
        }
    }
}
package edu.upc.dsa;

import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Types.*;
import java.util.List;

import static java.sql.Types.*;

public class SessionImpl implements Session {

    private Logger log = Logger.getLogger(SessionImpl.class.getName());
    private Connection connection;

    SessionImpl() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/dsaDDBB", "root", "root");
        } catch (Exception e) {
            log.error("Error exception");
            e.printStackTrace();
        }
    }

    public void save(Object entity) throws Exception {

        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();

        String query ="INSERT INTO " + entity.getClass().getSimpleName() +" (";
        for (Field f: fields) sb.append(f.getName()).append(",");
        query += sb.deleteCharAt(sb.length() - 1).toString();
        query += ") VALUES (";
        sb = new StringBuilder();
        for (Field f: fields) sb.append("?,");
        query += sb.deleteCharAt(sb.length() - 1).toString();
        query += ")";

        PreparedStatement prep = this.connection.prepareStatement(query);
        for (int i = 1; i < fields.length + 1; i++) prep.setString(i, new PropertyDescriptor(fields[i - 1].getName(), entity.getClass()).getReadMethod().invoke(entity).toString());
        prep.execute();
        prep.close();

        log.info("query: " + query);
    }

    public Object get(Class theClass, int id) {
        Statement stmt = null;
        ResultSet rs = null;
        Object object = null;
        ResultSetMetaData rsmd = null;

        try {
            stmt = this.connection.createStatement();
            if (stmt.execute("SELECT * FROM " + theClass.getSimpleName()+" WHERE id="+id)) {
                rs = stmt.getResultSet();
                rsmd = rs.getMetaData();

                if ((rs!=null) && rs.next()) {
                    System.out.println(rs.getString(1)+ " "+
                    rs.getString(2)+ " "+
                    rs.getString(3));

                    object = theClass.newInstance();

                    int nCols = rsmd.getColumnCount();
                    System.out.println("num Cols: "+nCols);

                    System.out.println(rsmd.getColumnLabel(1));
                    System.out.println(rsmd.getColumnLabel(2));
                    System.out.println(rsmd.getColumnLabel(3));
                    System.out.println(rsmd.getColumnType(1));
                    System.out.println(rsmd.getColumnType(2));
                    System.out.println(rsmd.getColumnType(3));

                    System.out.println("VARCHAR "+VARCHAR);
                    System.out.println("DATE "+ DATE);
                    System.out.println("INTEGER " + INTEGER);
                    int _id = rs.getInt(1);
                    String username = rs.getString(2);
                    String password = rs.getString(3);

                    ((User)object).setId(_id);
                    ((User)object).setUsername(username);
                    ((User)object).setPassword(password);


                    // object."setPassword"
                }


            }

            log.info(rs);
            return object;
        }
        catch (SQLException ex){
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error("VendorError: " + ex.getErrorCode());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
    }
        return null;
    }

    public void update(Object entity, int id) {
        //String query ="UPDATE" + entity.getClass().getSimpleName() + "SET";
        //System.out.println("query "+query);
    }

    public void delete(Object object, int id) {
        String query ="DELETE FROM " + object.getClass().getSimpleName() + "WHERE id = " + id;
        System.out.println("query "+query);
    }

    public List<Object> findAll(Class theClass) {
        return null;
    }

    public void close() throws Exception {
        this.connection.close();
    }
}

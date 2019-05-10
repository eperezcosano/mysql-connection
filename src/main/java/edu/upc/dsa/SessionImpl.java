package edu.upc.dsa;

import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Types.*;
import java.util.ArrayList;
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

    public Object get(Class theClass, int id) throws Exception {
        return find(theClass, id).get(0);
    }

    public List<Object> findAll(Class theClass) throws Exception {
        return find(theClass, 0);
    }

    public void update(Object entity, int id) {
        //String query ="UPDATE" + entity.getClass().getSimpleName() + "SET";
        //System.out.println("query "+query);
    }

    public void delete(Object object, int id) {
        String query ="DELETE FROM " + object.getClass().getSimpleName() + "WHERE id = " + id;
        System.out.println("query "+query);
    }

    public void close() throws Exception {
        this.connection.close();
    }

    private List<Object> find(Class theClass, int id) throws Exception {

        List<Object> res = new ArrayList<Object>();
        ResultSet rs;
        Object object;
        String query;
        if (id != 0) {
            query = "SELECT * FROM " + theClass.getSimpleName() + " WHERE id = ?";
            PreparedStatement prep = this.connection.prepareStatement(query);
            prep.setInt(1, id);
            prep.execute();
            rs = prep.getResultSet();
        }
        else {
            query = "SELECT * FROM " + theClass.getSimpleName();
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            rs = statement.getResultSet();
        }

        while (rs.next() != false) {

            object = theClass.newInstance();

            for (int i = 1; i <=rs.getMetaData().getColumnCount(); i++)
            {
                String columnName = rs.getMetaData().getColumnName(i);
                columnName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);

                switch (rs.getMetaData().getColumnType(i))
                {
                    case INTEGER:
                        int intValue = rs.getInt(i);
                        theClass.getMethod("set" + columnName, int.class).invoke(object, intValue);
                        break;
                    case VARCHAR:
                        String stringValue = rs.getString(i);
                        theClass.getMethod("set" + columnName, String.class).invoke(object, stringValue);
                        break;
                    case DATE:
                        Date dateValue = rs.getDate(i);
                        theClass.getMethod("set" + columnName, Date.class).invoke(object, dateValue);
                        break;
                    case BOOLEAN:
                        Boolean booleanValue = rs.getBoolean(i);
                        theClass.getMethod("set" + columnName, Boolean.class).invoke(object, booleanValue);
                        break;
                    default:
                        break;
                }
            }

            res.add(object);
        }

        return res;
    }

}

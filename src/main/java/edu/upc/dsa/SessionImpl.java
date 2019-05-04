package edu.upc.dsa;

import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

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

        log.info("query: " + query);

        PreparedStatement prep = this.connection.prepareStatement(query);

        for (int i = 1; i < fields.length + 1; i++) prep.setString(i, new PropertyDescriptor(fields[i - 1].getName(), entity.getClass()).getReadMethod().invoke(entity).toString());
        prep.execute();

        log.info("query: " + query);
    }

    public Object get(Class theClass, int id) {
        String query ="SELECT * FROM " + theClass.getSimpleName() + "WHERE id = " + id;
        System.out.println("query "+query);
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

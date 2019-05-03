package edu.upc.dsa;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;

public class SessionImpl implements Session {

    private Connection connection;

    public SessionImpl() throws Exception {
        this.connection = MysqlConn.getConnection();
    }

    public void save(Object entity) {

        String query ="INSERT INTO " + entity.getClass().getSimpleName()+" () VALUES ()";
        System.out.println("query "+query);

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field f: fields) {
            System.out.println(" "+f.getName()+",");

        }

        for (Field f: fields) {
            System.out.println(" ?,");

        }
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

package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.sql.Connection;
import java.util.List;

public class UsersDAO {

    private void addUser (String name, String password) {

        Session session = null;
        try {
            session = Factory.getSession();
            User user = new User(name, password);
            session.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<User> getAllUsers() {return null;}

    public static void main (String [] user ){
        UsersDAO dao = new UsersDAO();
        try {
            dao.addUser("Mario", "AAA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
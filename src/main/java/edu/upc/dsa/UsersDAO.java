package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.sql.Connection;
import java.util.List;

public class UsersDAO {

    public void addUser (String name, String password) {

        Session session = null;
        try {
            session = Factory.getSession();
            User user = new User(0, name, password);
            session.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (Exception e) {}

        }
    }

    public User getUser(int id) throws Exception {
        User user = null;
        Session session = null;
        try {
            session = Factory.getSession();
            user = (User)session.get(User.class, id);
            System.out.println("user: "+user);

        } catch (Exception e) {e.printStackTrace();}
        finally {
            if (session!=null) session.close();
        }

        return user;
    }

    public List<User> getAllUsers() {return null;}

    public static void main (String [] user ){
        UsersDAO dao = new UsersDAO();
        try {
            //dao.addUser("Mario", "AAA");
            dao.getUser(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
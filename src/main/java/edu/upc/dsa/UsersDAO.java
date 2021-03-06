package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.sql.Connection;
import java.util.ArrayList;
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

    public User get(int id) throws Exception {
        User user = null;
        Session session = null;
        try {
            session = Factory.getSession();
            user = (User)session.get(User.class, id);
        } catch (Exception e) {e.printStackTrace();}
        finally {
            if (session!=null) session.close();
        }

        return user;
    }

    public List<User> getAllUsers() throws Exception {

        List<User> users = null;
        Session session = null;
        try {
            session = Factory.getSession();
            users = (List<User>) (List) session.findAll(User.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return users;
    }

    public void deleteUser(int id) throws Exception {

        Session session = null;

        try {
            session = Factory.getSession();
            session.delete(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

    }

    public void updateUser(User user) throws Exception{

        Session session = null;

        try {
            session = Factory.getSession();
            session.update(user, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    public static void main (String [] user ){
        UsersDAO dao = new UsersDAO();
        try {
            //dao.addUser("Mario", "AAA");
            //List<User> res = dao.getAllUsers();
            //System.out.println(res);

            //User example = dao.get(2);
            //dao.deleteUser(4);
            //System.out.println(example);
            dao.updateUser(new User(1, "SuperIzan", "SuperPassword"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
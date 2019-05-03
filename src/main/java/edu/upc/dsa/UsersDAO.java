package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.List;

public class UsersDAO {

    public void addUser (String name, String address) {
        Session session = Factory.getSession();
        User user = new User(name, address);
        session.save(user);

        session.forceClose();
    }

    public User updateUser() {

    }

    public List<User> getAllUsers() {}

    public User getUsers (String id) {
        Session session = Factory.getSession();

        User user = session.getUsers(User.class, id);

        return user;

        session.forceClose();
    }

    public static void main (String [] user ){
        UsersDAO dao = new UsersDAO();
        dao.addUser("Mario", "AAA");
    }
}
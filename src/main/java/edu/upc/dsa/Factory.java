package edu.upc.dsa;

public class Factory {
    public static Session getSession() throws Exception {
        Session session = new SessionImpl();
        return session;
    }
}

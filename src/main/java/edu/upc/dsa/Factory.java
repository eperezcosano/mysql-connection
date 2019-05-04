package edu.upc.dsa;

public class Factory {
    public static Session getSession() throws Exception {
        return new SessionImpl();
    }
}

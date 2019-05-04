package edu.upc.dsa;

class Factory {
    static Session getSession() throws Exception {
        return new SessionImpl();
    }
}

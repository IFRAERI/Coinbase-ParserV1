package com.coinbase.connector;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        Application app = new Application();
        app.initialize();
        app.start();
    }
}
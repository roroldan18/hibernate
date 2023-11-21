package org.example;

import org.example.View.LoginView;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.buildSessionFactory();
        LoginView.mostrarLogin();
    }
}
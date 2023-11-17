package org.example;

import org.example.View.Menu;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.buildSessionFactory();
        Menu.mostrarPrincipal();
    }
}
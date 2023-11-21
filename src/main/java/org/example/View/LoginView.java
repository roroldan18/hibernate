package org.example.View;

import org.example.Controller.GestionBDUsuarios;
import org.example.Entity.Usuario;

import java.util.Scanner;

public class LoginView {
    private static Scanner sc = new Scanner(System.in);

    public static void mostrarLogin(){
        System.out.println("Bienvenido al gestor de contactos");
        int opcion = -1;

        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("0. Salir");
        System.out.println("Ingrese una opción: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch(opcion){
            case 1:
                mostrarIniciarSesion();
                break;
            case 2:
                mostrarRegistrarse();
                break;
            case 0:
                System.out.println("Hasta pronto...");
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }
    }

    private static void mostrarRegistrarse() {
        System.out.println("Ingrese su nombre de usuario: ");
        String nombreUsuario = sc.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String contrasena = sc.nextLine();
        System.out.println("Repita su contraseña: ");
        String contrasena2 = sc.nextLine();

        if(!contrasena.equals(contrasena2)){
            System.out.println("Las contraseñas no coinciden");
            mostrarLogin();
            return;
        }

        Usuario usuario = new Usuario(nombreUsuario, contrasena);
        GestionBDUsuarios gestionBDUsuarios = new GestionBDUsuarios();
        gestionBDUsuarios.register(usuario);
        mostrarLogin();
    }

    private static void mostrarIniciarSesion() {
        System.out.println("Ingrese su nombre de usuario: ");
        String nombreUsuario = sc.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String contrasena = sc.nextLine();

        GestionBDUsuarios gestionBDUsuarios = new GestionBDUsuarios();

        if(gestionBDUsuarios.login(nombreUsuario, contrasena)){
            System.out.println("Bienvenido " + nombreUsuario);
            Menu menu = new Menu();
            menu.mostrarPrincipal();
        } else {
            // Volver al menu anterior
            System.out.println("Usuario o contraseña incorrectos");
            mostrarLogin();
        }
    }
}

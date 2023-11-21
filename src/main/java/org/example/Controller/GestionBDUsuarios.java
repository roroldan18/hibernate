package org.example.Controller;

import org.example.Entity.Usuario;
import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GestionBDUsuarios {
    private Transaction transaction = null;
    private Session session = null;

    // Login
    public boolean login(String nombreUsuario, String contrasena){
        try{
            HibernateUtil.openSession();

            session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            Usuario usuario = (Usuario) session.createQuery("FROM Usuario WHERE usuario = :nombreUsuario").setParameter("nombreUsuario", nombreUsuario).uniqueResult();

            if(usuario != null){
                if(usuario.getPassword().equals(getMD5(contrasena))){
                    return true;
                }
            }
            return false;
        } catch (Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al iniciar sesión");
            e.printStackTrace();
            return false;
        }
        finally{
            if(session != null){
                session.close();
            }
        }

    }

    // Register
    public Usuario register(Usuario usuario){
        try{
            HibernateUtil.openSession();

            session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            Usuario newUser = new Usuario(usuario.getUsuario(), getMD5(usuario.getPassword()));

            session.save(newUser);
            transaction.commit();
            System.out.println("Usuario registrado");
            //Volver al menu anterior
            return usuario;
        } catch (Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al registrar usuario");
            e.printStackTrace();
            return null;
        }
        finally{
            if(session != null){
                session.close();
            }
        }

    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}

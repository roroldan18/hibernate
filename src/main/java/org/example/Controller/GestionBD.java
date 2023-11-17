package org.example.Controller;

import org.example.Entity.Contacto;
import org.example.Entity.UltimoEliminado;
import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class GestionBD {
    public boolean agregarContacto(Contacto contacto){
        try{
            HibernateUtil.openSession();
            HibernateUtil.getCurrentSession().save(contacto);
            HibernateUtil.getCurrentSession().getTransaction().commit();
            System.out.println("Contacto agregado");
            return true;
        } catch (Exception e){
            System.out.println("Error al agregar contacto");
            e.printStackTrace();
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            return false;
        } finally {
            HibernateUtil.closeSessionFactory();
        }
    }

    public void listarContactos(){
       Transaction transaction = null;

        try{
            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            List<Contacto> contactos = session.createQuery("FROM Contacto").list();

            for (Contacto contacto: contactos){
                System.out.println("ID: " + contacto.getId() + "\nNombre: " + contacto.getNombre() + "\nApellido: " + contacto.getApellido() + "\nEmail: " + contacto.getEmail() + "\nTeléfono: " + contacto.getTelefono());
                System.out.println("--------------------------------------------------");
            }
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }

            System.out.println("Error al listar contactos");
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSessionFactory();
        }
    }


    public Contacto buscarContacto(String nombre) {
        Transaction transaction = null;
        try {
            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            String hql = "FROM Contacto WHERE nombre = :nombre";
            Contacto contacto = (Contacto) session.createQuery(hql)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            transaction.commit();

            return contacto;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al buscar contacto");
            e.printStackTrace();
            return null;
        } finally {
            HibernateUtil.closeSessionFactory();
        }
    }


    public void editarContacto(Contacto contacto) {
        Transaction transaction = null;
        try{
            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            session.update(contacto);
            transaction.commit();
            System.out.println("Contacto actualizado");

        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("Error al actualizar el contacto");
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSessionFactory();
        }

    }

    private void insertUltimoEliminado(Contacto contacto){
        Transaction transaction = null;
        try{
            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            UltimoEliminado ultimoEliminado = new UltimoEliminado(contacto.getId(), contacto.getNombre(), contacto.getApellido(), contacto.getTelefono(), contacto.getEmail(), new java.sql.Timestamp(System.currentTimeMillis()));

            session.save(ultimoEliminado);
            transaction.commit();
            System.out.println("Contacto agregado a la tabla de ultimos eliminados");

        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("Error al insertar en la tabla de ultimos eliminados");
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSessionFactory();
        }
    }

    public void recuperarUltimoEliminado(){
        Transaction transaction = null;
        try{

            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            String hql = "FROM UltimoEliminado ORDER BY fecha DESC";
            UltimoEliminado ultimoEliminado = (UltimoEliminado) session.createQuery(hql).setMaxResults(1).uniqueResult();

            if(ultimoEliminado != null){
                Contacto contacto = new Contacto(ultimoEliminado.getId(), ultimoEliminado.getNombre(), ultimoEliminado.getApellido(), ultimoEliminado.getTelefono(), ultimoEliminado.getEmail());
                session.save(contacto);
                session.delete(ultimoEliminado);
                transaction.commit();
                System.out.println("Contacto recuperado");
            } else {
                System.out.println("No hay contactos para recuperar");
            }



        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("Error al eliminar el contacto");
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSessionFactory();
        }
    }

    public void eliminarContacto(Contacto contacto) {
        Transaction transaction = null;
        try{

            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            session.delete(contacto);
            transaction.commit();
            System.out.println("Contacto eliminado");

        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("Error al eliminar el contacto");
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSessionFactory();
        }
    }

    public void filtrarContactosLetraNombre(char letra) {
        Transaction transaction= null;
        try{

            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            String hql = "FROM Contacto WHERE nombre LIKE :letra";
            List<Contacto> contactos = session.createQuery(hql)
                    .setParameter("letra", letra + "%")
                    .list();

            for (Contacto contacto: contactos){
                System.out.println("ID: " + contacto.getId() + "\nNombre: " + contacto.getNombre() + "\nApellido: " + contacto.getApellido() + "\nEmail: " + contacto.getEmail() + "\nTeléfono: " + contacto.getTelefono());
                System.out.println("--------------------------------------------------");
            }
            transaction.commit();

        } catch (Exception e ){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("Error al filtrar contactos");
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSessionFactory();
        }
    }

    public void eliminarTodosLosDatos() {
        Transaction transaction = null;
        try{

            HibernateUtil.openSession();
            Session session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM Contacto").executeUpdate();
            transaction.commit();
            System.out.println("Contactos eliminados");

        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("Error al eliminar los contactos");
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSessionFactory();
        }
    }
}

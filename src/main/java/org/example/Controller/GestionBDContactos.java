package org.example.Controller;

import org.example.Entity.Contacto;
import org.example.Entity.UltimoEliminado;
import org.example.Entity.Usuario;
import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GestionBDContactos {
    private Transaction transaction = null;
    private Session session = null;
    public boolean agregarContacto(Contacto contacto){
        try{
            HibernateUtil.openSession();

            session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            session.save(contacto);
            transaction.commit();
            System.out.println("Contacto agregado");
            return true;
        } catch (Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al agregar contacto");
            e.printStackTrace();
            return false;
        }
        finally{
            if(session != null){
                session.close();
            }
        }
    }

    public void listarContactos(){
        try{
            HibernateUtil.openSession();
            session = HibernateUtil.getCurrentSession();
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
            if(session != null){
                session.close();
            }
        }
    }


    public Contacto buscarContacto(String nombre) {
        try {
            HibernateUtil.openSession();
            session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            String hql = "FROM Contacto WHERE nombre LIKE :nombre";

            Contacto contacto = (Contacto) session.createQuery(hql)
                    .setParameter("nombre", "%" + nombre + "%")
                    .setMaxResults(1)  // Usa setMaxResults para limitar a un resultado
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
            session.close();
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


    }

    private void insertUltimoEliminado(Contacto contacto){
        try{
            HibernateUtil.openSession();
            session = HibernateUtil.getCurrentSession();
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
            if(session != null){
                session.close();
            }
        }
    }

    public void recuperarUltimoEliminado(){
        try{

            HibernateUtil.openSession();
            session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            String hql = "FROM UltimoEliminado ORDER BY fecha_eliminacion DESC";
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
            if(session != null){
                session.close();
            }
        }
    }

    public void eliminarContacto(Contacto contacto) {
        try{

            HibernateUtil.openSession();
            session = HibernateUtil.getCurrentSession();
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
            if(session != null){
                session.close();
            }
        }
    }

    public void filtrarContactosLetraNombre(char letra) {
        try{

            HibernateUtil.openSession();
            session = HibernateUtil.getCurrentSession();
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
            if(session != null){
                session.close();
            }
        }

    }

    public void eliminarTodosLosDatos() {
        try{

            HibernateUtil.openSession();
            session = HibernateUtil.getCurrentSession();
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
            if(session != null){
                session.close();
            }
        }

    }
}

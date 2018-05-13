package Hibernate.lesson3;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Map;


public class GeneralDAO {

    private static SessionFactory sessionFactory;


    public static <T> void save(T t) {
        Transaction tr;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            session.save(t);
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("save is failed");
            System.err.println(e.getMessage());

        }
        System.out.println("save is done!");

    }

    public static <T> void update(T t) {

        Transaction tr = null;

        try (Session session = sessionFactory.openSession()) {

            tr = session.beginTransaction();
            session.update(t);
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("save is failed");
            e.printStackTrace();
            if (tr != null) tr.rollback();

        }
        System.out.println("update is done!");
    }


    public static void delete(String tableName, long id) {

        Transaction tr;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            session.delete(findById(tableName, id));
            tr.commit();

        } catch (HibernateException e) {
            System.err.println("delete is failed");
            System.err.println(e.getMessage());

        }
        System.out.println("delete is done!");


    }

    public static  <T> T findById(String tableName, long id) {

        T res = null;

        try (Session session = sessionFactory.openSession()) {

            Query query = session.createQuery("FROM " + tableName + " WHERE id = :id ");
            query.setParameter("id", id);
            res = (T) query.getSingleResult();

        } catch (HibernateException e) {
            System.err.println("find by id is failed");
            e.printStackTrace();
        }


        return res;
    }




    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
}
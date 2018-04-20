package Hibernate.lesson2.hw1;


import Hibernate.lesson2.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDAO {
    private SessionFactory sessionFactory;
    private HashMap<Integer, Object> putResults = new HashMap<>();

    public Product findById(Long id) {
        String findById = "from Product where id = ?";
        putResults.put(0, id);
        return genParameter(findById, putResults).get(0);
    }

    public List findByName(String name) {
        String findByName = "from Product where name = ?";
        putResults.put(0, name);
        return genParameter(findByName, putResults);
    }

    public List containedName(String name) {
        String containedName = "from Product where name like ?";
        putResults.put(0, "%" + name + "%");
        return genParameter(containedName, putResults);

    }

    public List findByPrice(Integer price, Integer delta) {
        String findByPrice = "from Product  where price >= ? and price <= ?";
        putResults.put(0, price - delta);
        putResults.put(1, price + delta);
        return genParameter(findByPrice, putResults);
    }

    public List findByNameSortedAsc(String name) {
        String findByNameSortedAsc = "from Product where name = ? order by name asc";
        putResults.put(0, name);
        return genParameter(findByNameSortedAsc, putResults);
    }

    public List findByNameSortedDesc(String name) {
        String findByNameSortedDesc = "from Product where name = ? order by name desc";
        putResults.put(0, name);
        return genParameter(findByNameSortedDesc, putResults);
    }


    public List findByPriceSortedDesc(Integer price, Integer delta) {

        String findByPrice = "from Product  where price >= ? and price <= ? order by price desc";
        putResults.put(0, price - delta);
        putResults.put(1, price + delta);
        return genParameter(findByPrice, putResults);
    }

    private List<Product> genParameter(String hql, HashMap<Integer, Object> objectMap) {
        List<Product> products = null;
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(hql);
            for (Map.Entry<Integer, Object> entry : objectMap.entrySet())
                query.setParameter(entry.getKey(), entry.getValue());
            products = query.getResultList();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return products;
    }


    private SessionFactory createSessionFactory() {
        //Singleton pattern
        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

}

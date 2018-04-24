package Hibernate.lesson2.hw1;


import Hibernate.lesson2.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDAO {
    private SessionFactory sessionFactory;
    private String FIND_BY_ID = "FROM Product WHERE ID = :ID";
    private String FIND_BY_NAME = "FROM Product WHERE NAME = :NAME?";
    private String CONTAINED_NAME = "FROM Product WHERE NAME LIKE?";
    private String FIND_BY_PRICE = "FROM Product WHERE PRICE  >= DIFDELTA AND PRICE <= SUMDELTA";
    private String FIND_BY_NAME_SORTED_ASC = "FROM Product WHERE NAME = :NAME ORDER BY NAME ASC";
    private String FIND_BY_NAME_SORTED_DESC = "FROM Product WHERE NAME = :NAME ORDER BY NAME DESCX";
    private String FIND_BY_PRICE_SORTED_DESC = "FROM Product  WHERE PRICE >= DIFDELTA AND PRICE <= SUMDELTA ORDER BY PRICE DESC";

    public Product findById(Long id) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("ID", id);
        return genParameter(FIND_BY_ID, putResults).get(0);
    }

    public List findByName(String name) {
        HashMap<String, Object> putResults = new HashMap<>();
        String findByName = "from Product where name = ?";
        putResults.put("NAME", name);
        return genParameter(FIND_BY_NAME, putResults);
    }

    public List containedName(String name) {
        HashMap<String, Object> putResults = new HashMap<>();
        String containedName = "from Product where name like ?";
        putResults.put("NAME", "%" + name + "%");
        return genParameter(CONTAINED_NAME, putResults);

    }

    public List findByPrice(Integer price, Integer delta) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("DIFDELTA", price - delta);
        putResults.put("SUMDELTA", price + delta);
        return genParameter(FIND_BY_PRICE, putResults);
    }

    public List findByNameSortedAsc(String name) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("NAME", name);
        return genParameter(FIND_BY_NAME_SORTED_ASC, putResults);
    }

    public List findByNameSortedDesc(String name) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("NAME", name);
        return genParameter(FIND_BY_NAME_SORTED_DESC, putResults);
    }


    public List findByPriceSortedDesc(Integer price, Integer delta) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("DIFDELTA", price - delta);
        putResults.put("SUMDELTA", price + delta);
        return genParameter(FIND_BY_PRICE_SORTED_DESC, putResults);
    }

    private List<Product> genParameter(String hql, HashMap<String, Object> objectMap) {
        List<Product> products = null;
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(hql);
            for (Map.Entry<String, Object> entry : objectMap.entrySet())
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

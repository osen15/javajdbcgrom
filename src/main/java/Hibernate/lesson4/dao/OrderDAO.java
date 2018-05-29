package Hibernate.lesson4.dao;

import Hibernate.lesson4.entities.Order;

public class OrderDAO {
    public void save(Order order)  {
        GeneralDAO.save(order);
    }

    public void delete(long id) {
        GeneralDAO.delete("ORDER4", id);
    }


    public void update(Order order) {
        GeneralDAO.update(order);
    }

    public void findById(long id) {
        GeneralDAO.findById("ORDER4", id);
    }

}

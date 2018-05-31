package Hibernate.lesson4.dao;

import Hibernate.lesson4.entities.Order;

import java.util.HashMap;
import java.util.List;


public class OrderDAO {


    private static String FIND_ROOMID_AND_USER_ID_IN_ORDER = "from Order where user = :userId and room = :roomId";

    public static void save(Order order) {
        GeneralDAO.save(order);
    }

    public static void delete(long id) {
        GeneralDAO.delete("Order", id);
    }


    public static void update(Order order) {
        GeneralDAO.update(order);
    }

    public static void findById(long id) {
        GeneralDAO.findById("Order", id);
    }


    public static long findRoomAndUser(long roomId, long userId) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("userId", userId);
        putResults.put("roomId", roomId);
        Order order = (Order) GeneralDAO.setParameter(FIND_ROOMID_AND_USER_ID_IN_ORDER, putResults).get(0);
        return order.getId();

    }

}

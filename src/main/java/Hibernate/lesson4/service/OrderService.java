package Hibernate.lesson4.service;

import Hibernate.lesson4.dao.OrderDAO;
import Hibernate.lesson4.dao.RoomDAO;
import Hibernate.lesson4.dao.UserDAO;
import Hibernate.lesson4.entities.Order;
import Hibernate.lesson4.entities.Room;
import Hibernate.lesson4.entities.User;


import java.util.Date;

public class OrderService {

    public static void bookRoom(long roomId, long userId, Date dateTo) {
        Room room = RoomDAO.findById(roomId);

        User user = UserDAO.findById(userId);

        Order order = new Order();
        order.setRoom(room);
        order.setUser(user);

        order.setDateFrom(room.getDateAvailableFrom());

        order.setDateTo(new Date(order.getDateFrom().getTime()
                + (dateTo.getTime() - order.getDateFrom().getTime())));

        order.setMoneyPaid(room.getPrice());

        OrderDAO.save(order);


    }


    public static void cancelReservation(long roomId, long userId) {
        OrderDAO.delete(OrderDAO.findRoomAndUser(roomId, userId));
        Room room = RoomDAO.findById(roomId);
        room.setDateAvailableFrom(new Date());
        RoomDAO.update(room);

        }


}

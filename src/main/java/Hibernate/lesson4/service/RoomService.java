package Hibernate.lesson4.service;

import Hibernate.lesson4.dao.RoomDAO;
import Hibernate.lesson4.entities.Filter;

import java.util.List;

public class RoomService {

    public static List findRooms(Filter filter) {


        return RoomDAO.roomFilter(filter);
    }




}

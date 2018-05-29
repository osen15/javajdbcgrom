package Hibernate.lesson4.controller;

import Hibernate.lesson4.dao.RoomDAO;
import Hibernate.lesson4.entities.Filter;
import Hibernate.lesson4.service.RoomService;

import java.util.List;

public class RoomController {

    public static List findRooms(Filter filter) {
        return RoomService.findRooms(filter);
    }


}

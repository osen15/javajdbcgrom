package Hibernate.lesson4.service;

import Hibernate.lesson4.dao.HotelDAO;

public class HotelService {

    public static void findHotelsByName(String name){
        HotelDAO.findHotelByName(name);
    }

    public static void findHotelsCity(String city){
        HotelDAO.findHotelByCity(city);
    }
}

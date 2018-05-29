package Hibernate.lesson4.controller;

import Hibernate.lesson4.service.HotelService;

public class HotelController {
    public static void findHotelByName(String name) {
        HotelService.findHotelsByName(name);
    }

    public static void findHotelByCity(String city) {
        HotelService.findHotelsCity(city);
    }


}

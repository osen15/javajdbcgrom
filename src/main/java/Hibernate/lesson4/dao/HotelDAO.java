package Hibernate.lesson4.dao;

import Hibernate.lesson4.entities.Hotel;

import java.util.HashMap;
import java.util.List;

public class HotelDAO {


    public void save(Hotel hotel) {
        GeneralDAO.save(hotel);
    }

    public void delete(long id) {
        GeneralDAO.delete("Hotel", id);
    }


    public void update(Hotel hotel) {
        GeneralDAO.update(hotel);
    }

    public void findById(long id) {
        GeneralDAO.findById("Hotel", id);
    }


    public static List findHotelByName(String name) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("name", name);
        String FIND_BY_NAME = "from Hotel where name = :name";
        return GeneralDAO.setParameter(FIND_BY_NAME, putResults);
    }
    public static List findHotelByCity(String city) {
        HashMap<String, Object> putResults = new HashMap<>();
        putResults.put("city", city);
        String FIND_BY_NAME = "from Hotel where city = :city";
        return GeneralDAO.setParameter(FIND_BY_NAME, putResults);
    }


}

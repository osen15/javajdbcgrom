package Hibernate.lesson4.dao;

import Hibernate.lesson4.entities.Filter;
import Hibernate.lesson4.entities.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;

public class RoomDAO {
    private static Map<String, Object> parameters = new HashMap<>();
    private static StringBuffer queryBuf = new StringBuffer("from Room as r join r.hotel as h ");
    private static boolean firstPosition = true;


    public static void save(Room room) {
        GeneralDAO.save(room);
    }

    public static void delete(long id) {
        GeneralDAO.delete("Room", id);
    }

    public static void update(Room room) {
        GeneralDAO.update(room);
    }

    public static Room findById(long id) {
      return   GeneralDAO.findById("Room", id);

    }


    public static List roomFilter(Filter filter) {

        if (filter.getNumberOfGuests() > 0) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("r.numberOfGuests = :numberOfGuests");
            parameters.put("numberOfGuests", filter.getNumberOfGuests());
            firstPosition = false;
        }
        if (filter.getPrice() > 0) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("r.price = :price");
            parameters.put("price", filter.getPrice());
            firstPosition = false;
        }
        if (filter.getBreakfastIncluded() == 1) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("r.breakfastIncluded = :breakfastIncluded");
            parameters.put("breakfastIncluded", filter.getBreakfastIncluded());
            firstPosition = false;
        }
        if (filter.getPetsAllowed() == 1) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("r.petsAllowed = :petsAllowed");
            parameters.put("petsAllowed", filter.getPetsAllowed());
            firstPosition = false;
        }
        if (filter.getDateAvailableFrom() != null) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("r.dateAvailableFrom = :dateAvailableFrom");
            parameters.put("dateAvailableFrom", filter.getDateAvailableFrom());
            firstPosition = false;
        }
        if (filter.getHotelName() != null) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("h.name = :name");
            parameters.put("name", filter.getHotelName());
            firstPosition = false;
        }

        if (filter.getHotelCountry() != null) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("h.country = :country");
            parameters.put("country", filter.getHotelCountry());
            firstPosition = false;
        }
        if (filter.getHotelCity() != null) {
            queryBuf.append(firstPosition ? " where " : " and ");
            queryBuf.append("h.city = :city");
            parameters.put("city", filter.getHotelCity());
            firstPosition = false;
        }

        System.out.println(queryBuf.toString());


        try (Session session = GeneralDAO.createSessionFactory().openSession()) {
            String hqlQuery = queryBuf.toString();
            Query query = session.createQuery(hqlQuery);

            iterationParateters(parameters, query);

           return query.getResultList();

        } catch (HibernateException e) {
            e.printStackTrace();
        }


        return null;
    }


    private static void iterationParateters(Map<String, Object> parameters, Query query) {
        for (Object o : parameters.keySet()) {
            String name = (String) o;
            Object value = parameters.get(name);
            query.setParameter(name, value);
        }


    }
}





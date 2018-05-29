package Hibernate.lesson4;


import Hibernate.lesson4.dao.HotelDAO;
import Hibernate.lesson4.dao.RoomDAO;

import Hibernate.lesson4.dao.UserDAO;
import Hibernate.lesson4.entities.Filter;
import Hibernate.lesson4.entities.Hotel;
import Hibernate.lesson4.entities.Room;
import Hibernate.lesson4.entities.User;

import java.util.Date;

public class Demo {
    public static void main(String[] args) {


        HotelDAO hotelDAO = new HotelDAO();

        Hotel hotel = new Hotel();
        hotel.setId((long) 1);
        hotel.setCountry("test");
        hotel.setCity("tesr");
        hotel.setName("test");
        hotel.setStreet("test");
        // hotelDAO.save(hotel);

        RoomDAO roomDAO = new RoomDAO();
        Room room = new Room();
        room.setPrice(100);
        room.setBreakfastIncluded(1);
        room.setDateAvailableFrom(new Date());
        room.setHotel(hotel);
        room.setPetsAllowed(1);
        room.setNumberOfGuests(4);
        // roomDAO.save(room);


        UserDAO userDAO = new UserDAO();
        // System.out.println(userDAO.findNameAndPass("test", "1111").toString());


        User user = new User();
        user.setId(3);
        user.setUserName("Oleg");
        user.setPassword("1111");
        user.setCountry("Ukr");
        user.setUserType(UserType.USER);
        //userDAO.save(user);


        Filter filter = new Filter("Opera");
        RoomDAO.roomFilter(filter);


    }

}

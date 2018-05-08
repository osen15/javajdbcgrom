package Hibernate.lesson3;

import java.util.Date;

public class Demo {
    public static void main(String[] args) {
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = new Hotel();
        hotel.setId((long) 1);
        hotel.setName("test");
        hotel.setCountry("test$$$$$");
        hotel.setCity("test");
        hotel.setStreet("test");

       // hotelDAO.save(hotel);
       // hotelDAO.delete(1);
        //hotelDAO.update(hotel);
       // hotelDAO.findById(1);

       RoomDAO roomDAO = new RoomDAO();
       Room room = new Room();
       room.setId((long) 2);
       room.setNumberOfGuests(2);
       room.setPrice((double) 1000);
       room.setBreakfastIncluded(1);
       room.setPetsAllowed(1);
       room.setDateAvaliableFrom(new Date());
       room.setHotel(hotel);

      // roomDAO.save(room);
      // roomDAO.delete(2);
      // roomDAO.update(room);
     //  roomDAO.findById(1);




    }
}
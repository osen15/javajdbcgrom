package Hibernate.lesson3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class RoomDAO {
    private String FIND_ROOMS_BY_HOTEL_ID = "FROM Room WHERE HOTEL_ID =:id";


    public void save(Room room) {
        GeneralDAO.save(room);
    }

    public void delete(long id) {
        GeneralDAO.delete("ROOM", id);
    }

    public void update(Room room) {
        GeneralDAO.update(room);
    }

    public void findById(long id) {
        GeneralDAO.findById("ROOM", id);
    }


    public List<Room> roomsByHotelId(long hotelId) {
        List<Room> rooms = null;
        try (Session session = GeneralDAO.createSessionFactory().openSession()) {
            Query query = session.createQuery(FIND_ROOMS_BY_HOTEL_ID);
            query.setParameter("id", hotelId);

            rooms = query.getResultList();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return rooms;
    }


}

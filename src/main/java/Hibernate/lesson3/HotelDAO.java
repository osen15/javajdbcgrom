package Hibernate.lesson3;


import org.hibernate.Transaction;

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
        GeneralDAO.findById("HOTEL", id);
    }


}

package Hibernate.lesson3;


public class HotelDAO {


    public void save(Hotel hotel) {
        GeneralDAO.save(hotel);
    }

    public void delete(long id) {
        GeneralDAO.delete("HOTEL", id);
    }

    public void update(Hotel hotel) {
        GeneralDAO.update(hotel);
    }

    public void findById(long id) {
        GeneralDAO.findById("HOTEL", id);
    }


}

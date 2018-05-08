package Hibernate.lesson3;

public class RoomDAO {


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


}

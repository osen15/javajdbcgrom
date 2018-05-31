package Hibernate.lesson4.dao;


import Hibernate.lesson4.entities.User;

public class UserDAO {


    public static void save(User user) {
        GeneralDAO.save(user);
    }

    public static void delete(long id) {
        GeneralDAO.delete("User", id);
    }


    public static void update(User user) {
        GeneralDAO.update(user);
    }

    public static User  findById(long id) {

        return GeneralDAO.findById("User", id);
    }


    public static User findNameAndPass(String name, String password){
       return GeneralDAO.findUserByNameAndPass("User", name, password);





    }






}

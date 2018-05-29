package Hibernate.lesson4.service;

import Hibernate.lesson4.dao.UserDAO;
import Hibernate.lesson4.entities.User;

public class UserService {

    private static boolean online;



    public static void registerUser(User user) throws  NullPointerException{
        if (user == null)
            throw  new NullPointerException("user is null");
        UserDAO.save(user);


    }


    public static void login(String name, String password) throws Exception {
        if (isOnline()) {

            throw new Exception("Other user is online");
        }
        User user = UserDAO.findNameAndPass(name, password);
        setOnline(true);
        System.out.println(user.toString() + ": is login and " + online);

    }


    public static void logout(){
        UserService.setOnline(false);
    }


    private static boolean isOnline() {
        return online = false;
    }


    private static void setOnline(boolean online) {
        UserService.online = online;
    }
}

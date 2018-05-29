package Hibernate.lesson4.controller;

import Hibernate.lesson4.entities.User;
import Hibernate.lesson4.service.UserService;

public class UserController {
    public static void userRegister(User user) {
        UserService.registerUser(user);
    }

    public static void login(String name, String password) throws Exception {
        UserService.login(name, password);
    }

    public static void logout() {
        UserService.logout();
    }

}

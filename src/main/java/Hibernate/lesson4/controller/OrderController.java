package Hibernate.lesson4.controller;

import Hibernate.lesson4.service.OrderService;

import java.util.Date;

public class OrderController {

    public static void bookRoom(long roomId, long userId, Date dateTo){
        OrderService.bookRoom(roomId,userId, dateTo);
    }


  //  public static void cancelReservation(long roomId, long userId) {
   //     OrderService.cancelReservation(roomId, userId);
   // }




}

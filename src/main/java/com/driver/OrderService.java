package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
    @Autowired
    OrderRepository repository;

    public void addOrder(Order order){
        repository.addOrder(order);
    }
    public void addPartner(String partnerId){
        repository.addPartner(partnerId);
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        repository.addOrderPartnerPair(orderId,partnerId);
    }
    public Order getOrderById(String orderId){
        return repository.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return repository.getPartnerById(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        return repository.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return  repository.getOrdersByPartnerId(partnerId);
    }
    public List<String> getAllOrders(){
        return repository.getAllOrders();
    }
    public Integer getCountOfUnassignedOrders(){
        return repository.getCountOfUnassignedOrders();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        Integer newtime = Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3,5));
        return repository.getOrdersLeftAfterGivenTimeByPartnerId(newtime, partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        Integer time = repository.getLastDeliveryTimeByPartnerId(partnerId);
        Integer hour = time/60;
        Integer minute = time%60;//lastTime - (hour*60);
        String hourInString = String.valueOf(hour);
        String minInString = String.valueOf(minute);
        if(hourInString.length() == 1){
            hourInString = '0' + hourInString;
        }
        if(minInString.length() == 1){
            minInString = '0' + minInString;
        }

        return  hourInString + ':' + minInString;
    }
    public void deletePartnerById(String partnerId){
        repository.deletePartnerById(partnerId);
    }
    public void deleteOrderById(String orderId){
        repository.deletePartnerById(orderId);
    }
}

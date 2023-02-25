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
    public int getOrderCountByPartnerId(String partnerId){
        return repository.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return  repository.getOrdersByPartnerId(partnerId);
    }
    public List<String> getAllOrders(){
        return repository.getAllOrders();
    }
    public int getCountOfUnassignedOrders(){
        return repository.getCountOfUnassignedOrders();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return repository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return repository.getLastDeliveryTimeByPartnerId(partnerId);
    }
    public void deletePartnerById(String partnerId){
        repository.deletePartnerById(partnerId);
    }
    public void deleteOrderById(String orderId){
        repository.deleteOrderById(orderId);
    }
}

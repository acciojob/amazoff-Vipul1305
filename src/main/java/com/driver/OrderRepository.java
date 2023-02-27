package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
@Repository
public class OrderRepository {

    HashMap<String,Order> orderDB = new HashMap<>();
    HashMap<String,DeliveryPartner> partnerDB = new HashMap<>();
    HashMap<String,String>  orderPartnerDB = new HashMap<>();
    HashMap<String,List<String>> assigOrderToPartnerDB = new HashMap<>();

    public void addOrder(Order order){
        orderDB.put(order.getId(),order);
    }
    public void addPartner(String partnerId){
        partnerDB.put(partnerId,new DeliveryPartner(partnerId));
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        if(orderDB.containsKey(orderId) && partnerDB.containsKey(partnerId)){
            List<String> list = new ArrayList<>();
            if(assigOrderToPartnerDB.containsKey(partnerId)) {
                list = assigOrderToPartnerDB.get(partnerId);
            }
            list.add(orderId);
            assigOrderToPartnerDB.put(partnerId,list);

            orderPartnerDB.put(orderId,partnerId);

            DeliveryPartner d = partnerDB.get(partnerId);
            d.setNumberOfOrders(list.size());
        }
    }
    public Order getOrderById(String orderId){
        return orderDB.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
       return partnerDB.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        return assigOrderToPartnerDB.get(partnerId).size();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return assigOrderToPartnerDB.get(partnerId);
    }
    public List<String> getAllOrders(){
//        List<String> list = new ArrayList<>();
//        for (String x: orderDB.keySet()){
//            list.add(x);
//        }
//        return list;
        return new ArrayList<>(orderDB.keySet());
    }
    public Integer getCountOfUnassignedOrders(){
        return orderDB.size() - orderPartnerDB.size();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(Integer time, String partnerId){
        List<String> list = assigOrderToPartnerDB.get(partnerId);
        Integer count = 0;

        for (String orderList: list){
            Order order = orderDB.get(orderList);
            if (time < order.getDeliveryTime()){
                count++;
            }
        }
        return count;
    }
    public Integer getLastDeliveryTimeByPartnerId(String partnerId){
        List<String> list = assigOrderToPartnerDB.get(partnerId);
        Integer lastTime = 0;
        for(String s: list){
            Integer time = orderDB.get(s).getDeliveryTime();
            lastTime = Math.max(lastTime,time);
        }
        return lastTime;
    }
    public void deletePartnerById(String partnerId){
        List<String> list = assigOrderToPartnerDB.get(partnerId);
        assigOrderToPartnerDB.remove(partnerId);
        for (String s: list) {
            orderPartnerDB.remove(s);
        }
        partnerDB.remove(partnerId);
    }
    public void deleteOrderById(String orderId){
        orderDB.remove(orderId);

        String partnerID = orderPartnerDB.get(orderId);
        orderPartnerDB.remove(orderId);

        assigOrderToPartnerDB.get(partnerID).remove(orderId);
        //decrease no of order from DeliveryPaitrtner
        DeliveryPartner d = partnerDB.get(partnerID);
        d.setNumberOfOrders(d.getNumberOfOrders()-1);
    }
}

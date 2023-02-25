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
    HashMap<String,List<String>> assigOrderToPartnerDB = new HashMap<>();
    HashSet<String> unassignedOrderDB = new HashSet<>();

    public void addOrder(Order order){
        orderDB.put(order.getId(),order);
        unassignedOrderDB.add(order.getId());// track of unassigned order
    }
    public void addPartner(String partnerId){
        partnerDB.put(partnerId,new DeliveryPartner(partnerId));
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        if(assigOrderToPartnerDB.containsKey(partnerId)){
            List<String> list= assigOrderToPartnerDB.get(partnerId);
            list.add(orderId);
            assigOrderToPartnerDB.put(partnerId,list);
        }else {
            List<String> list = new ArrayList<>();
            list.add(orderId);
            assigOrderToPartnerDB.put(partnerId,list);
        }
        DeliveryPartner d = partnerDB.get(partnerId);
        d.setNumberOfOrders(assigOrderToPartnerDB.get(partnerId).size());

        // unassigned order
        unassignedOrderDB.remove(orderId);
    }
    public Order getOrderById(String orderId){
        return orderDB.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
       return partnerDB.get(partnerId);
    }
    public int getOrderCountByPartnerId(String partnerId){
        return partnerDB.get(partnerId).getNumberOfOrders();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return assigOrderToPartnerDB.get(partnerId);

    }
    public List<String> getAllOrders(){
        List<String> list = new ArrayList<>();
        for (String x: orderDB.keySet()){
            list.add(x);
        }
        return list;
    }
    public int getCountOfUnassignedOrders(){
        return unassignedOrderDB.size();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        List<String> list = assigOrderToPartnerDB.get(partnerId);
        int count = 0;
        int time2 = Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3,5));
        for (String orderList: list){
            Order order = orderDB.get(orderList);
            if (time2 < order.getDeliveryTime()){
                count++;
            }
        }
        return count;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        List<String> list = assigOrderToPartnerDB.get(partnerId);
        int lastTime = 0;
        for(String s: list){
            int time = orderDB.get(s).getDeliveryTime();
            lastTime = Math.max(lastTime,time);
        }
        int hour = lastTime/60;
        int minute = lastTime - (hour*60);
        String ans = "";
        if((hour/10)==0) {
            ans += "0"+hour;
        }else {
            ans += hour;
        }
        if ((minute/10)==0){
            ans += "0"+minute;
        }else {
            ans += minute;
        }
        return ans;
    }
    public void deletePartnerById(String partnerId){
        List<String> list = assigOrderToPartnerDB.get(partnerId);
        unassignedOrderDB.addAll(list);
        assigOrderToPartnerDB.remove(partnerId);
        partnerDB.remove(partnerId);
    }
    public void deleteOrderById(String orderId){
        for (String partnerID : assigOrderToPartnerDB.keySet()){
            List<String> list = assigOrderToPartnerDB.get(partnerID);
            for (String s: list){
                if (orderId.equals(s)){
                    //corresponding partner should be unassigned
                    list.remove(orderId);
                    assigOrderToPartnerDB.put(partnerID,list);
                    //decrease no of order from DeliveryPartner
                    DeliveryPartner d = partnerDB.get(partnerID);
                    d.setNumberOfOrders(d.getNumberOfOrders()-1);
                }
            }
        }
    }
}

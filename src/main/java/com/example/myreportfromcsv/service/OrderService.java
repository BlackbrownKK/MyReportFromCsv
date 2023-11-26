package com.example.myreportfromcsv.service;

import com.example.myreportfromcsv.model.Order;
import com.example.myreportfromcsv.repasitory.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {
    private final ArrayList<Order> orders;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(ArrayList<Order> orders, OrderRepository orderRepository) {
        this.orders = orders;
        this.orderRepository = orderRepository;
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }
    public Iterable<Order> getAll(){
        return orderRepository.findAll();
    }

    public void addAllInfoToDB() {
        int count = 0;
        for (Order ord : orders) {
            if (!orderRepository.existsById(ord.getOrderId())) {
                orderRepository.save(ord);
                count++;
            }
        }
        System.out.println(count);
    }

    public Order findById(long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            // You can throw an exception or handle the absence of the order as needed
            throw new RuntimeException("Order not found for id: " + id);
        }
    }

}

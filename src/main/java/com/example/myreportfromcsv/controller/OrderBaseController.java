package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.model.Order;
import com.example.myreportfromcsv.repasitory.OrderRepository;
import com.example.myreportfromcsv.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderBaseController {
    private final ModelController controller;
    private final OrderRepository orderRepository;
    private OrderService orderService;

    @Autowired
    public OrderBaseController(ModelController controller, OrderRepository orderRepository) {
        this.controller = controller;
        this.orderRepository = orderRepository;
        orderService = new OrderService(controller.makeModels(), orderRepository);
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id) {
        return orderService.findById(id).toString();
    }

    @GetMapping("/inProgress")
    public String getReportInProgress() {
        List<String> result = new ArrayList<>();
        Iterable<Order> collectionOfOrders = orderService.getAll();
        collectionOfOrders.forEach(order -> {
            if (order.isInTheProgress()) {
                result.add(order.toString()); // Assuming toString provides relevant information
            }
        });
        return result.toString();
    }

    public void delAllInfoToDB() {
        orderService.deleteAll();
    }
    @GetMapping("/addAllInfoToDB")
    public String addAllInfoToDB() {
        orderService.addAllInfoToDB();
        return "addAllInfoToDB";
    }
}
package com.example.myreportfromcsv.controller;

import ch.qos.logback.core.model.Model;
import com.example.myreportfromcsv.repasitory.OrderRepository;
import com.example.myreportfromcsv.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RequestMapping("/addAll")
public class OrderController {
    private final ModelController controller;
    private final OrderRepository orderRepository;
@Autowired
    public OrderController(ModelController controller, OrderRepository orderRepository) {
        this.controller = controller;
        this.orderRepository = orderRepository;
        OrderService service = new OrderService(controller.makeModels(), orderRepository);
        service.deleteAll();
        service.addAllInfoToDB();
    }
}
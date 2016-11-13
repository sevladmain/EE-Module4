package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by SeVlad on 13.11.2016.
 */
@Controller
public class OrderController {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order/allOpen", method = RequestMethod.GET)
    public String getAllOpenOrders(Model model){
        LOGGER.debug("getAllOpenOrders() is executed!");
        model.addAttribute("orders", orderService.getAllOpenOrders());
        model.addAttribute("isClosed", "OPEN");

        return "app.orders";
    }

    @RequestMapping(value = "/order/allClosed", method = RequestMethod.GET)
    public String getAllClosedOrders(Model model){
        LOGGER.debug("getAllClosedOrders() is executed!");
        model.addAttribute("orders", orderService.getAllClosedOrders());
        model.addAttribute("isClosed", "CLOSED");

        return "app.orders";
    }

}

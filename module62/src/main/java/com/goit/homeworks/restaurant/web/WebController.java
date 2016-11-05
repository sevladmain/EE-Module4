package com.goit.homeworks.restaurant.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by SeVlad on 05.11.2016.
 */
@Controller
public class WebController {
    private final Logger LOGGER = Logger.getLogger(WebController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Map<String, Object> model) {

        LOGGER.debug("default() is executed!");

        return "app.homepage";
    }
}

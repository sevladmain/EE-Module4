package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.services.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by SeVlad on 12.11.2016.
 */
@Controller
public class MenuController {
    private final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
    @RequestMapping(value = "/menu/all", method = RequestMethod.GET)
    public String getAllMenus(Model model){
        LOGGER.debug("getAllMenus() is executed!");
        model.addAttribute("menus", menuService.getAllMenus());
        return "app.menus";
    }
}

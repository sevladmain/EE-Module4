package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.services.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * Created by SeVlad on 10.11.2016.
 */
@Controller
public class DishController {
    private final Logger LOGGER = LoggerFactory.getLogger(DishController.class);
    @Autowired
    private DishService dishService;

    public DishService getDishService() {
        return dishService;
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @RequestMapping(value = "/dish/all", method = RequestMethod.GET)
    public String getAllDishes(Map<String, Object> model) {

        LOGGER.info("getAllDishes() is executed!");
        model.put("dishes", dishService.getAllDishes());
        return "app.dishes";
    }

    @RequestMapping(value = "/dish/{id}/delete", method = RequestMethod.POST)
    public String deleteDish(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

        LOGGER.debug("deleteDish() is executed!");
        Dish dish = dishService.getDishById(id);
        dishService.deleteDish(dish);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Страва " + dish.getName()
                + " видалена з бази даних");
        return "redirect:/dish/all";
    }


}

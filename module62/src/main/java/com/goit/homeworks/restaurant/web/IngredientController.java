package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.services.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by SeVlad on 19.11.2016.
 */
@Controller
public class IngredientController {
    private final Logger LOGGER = LoggerFactory.getLogger(IngredientController.class);

    @Autowired
    IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @RequestMapping(value = "/ingredient/all", method = RequestMethod.GET)
    public String getAllIngredients(Model model){
        LOGGER.debug("getAllIngredients()");

        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        return "app.ingredients";
    }



}

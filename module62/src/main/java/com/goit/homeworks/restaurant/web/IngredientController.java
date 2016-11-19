package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.core.Ingredient;
import com.goit.homeworks.restaurant.services.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getAllIngredients(Model model) {
        LOGGER.debug("getAllIngredients()");

        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        return "app.ingredients";
    }

    @RequestMapping(value = "/ingredient/{id}/delete", method = RequestMethod.POST)
    public String deleteIngredient(@PathVariable("id") int id,
                                   final RedirectAttributes redirectAttributes) {
        LOGGER.debug("deleteIngredient()");
        Ingredient ingredient = ingredientService.getIngredientById(id);
        ingredientService.deleteIngredient(ingredient);
        redirectAttributes.addAttribute("css", "success");
        redirectAttributes.addAttribute("msg", "Складова " + ingredient.getName() +
                " успішно видалена");

        return "redirect:/ingredient/all";
    }

    @RequestMapping(value = "ingredient/find", method = RequestMethod.GET)
    public String findIngredients(Model model){
        LOGGER.debug("findIngredients()");

        model.addAttribute("findWhat", "INGREDIENT");
        return "app.find";
    }

    @RequestMapping(value = "ingredient/finded", method = RequestMethod.POST)
    public String findedIngredients(@ModelAttribute("findString") String findString,
                                    Model model){
        LOGGER.debug("findedIngredients()");

        model.addAttribute("ingredients", ingredientService.findIngredientByName(findString));
        return "app.ingredients";
    }

}

package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Menu;
import com.goit.homeworks.restaurant.services.MenuService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

import java.util.Map;

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

    @RequestMapping(value = "/menu/{id}/delete", method = RequestMethod.POST)
    public String deleteMenus(@PathVariable("id") int id, final RedirectAttributes redirectAttributes){
        LOGGER.debug("deleteMenus() is executed!");
        String css;
        String msg;
        Menu menu = menuService.getMenuById(id);
        if(menuService.menuHasDishes(id)){
            css = "warning";
            msg = "Меню "+ menu.getName() + " не порожнє. Спочатку видаліть з нього всі страви";
        } else{
            menuService.deleteMenu(menu);
            css = "success";
            msg = "Меню " + menu.getName() + " успішно видалено";
        }
        redirectAttributes.addFlashAttribute("css", css);
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/menu/all";
    }

    @RequestMapping(value = "/menu/add", method = RequestMethod.GET)
    public String showCreateMenuForm(Model model){
        LOGGER.debug("showCreateMenuForm() is executed!");
        Menu menu = new Menu();
        model.addAttribute("menuForm", menu);
        return "app.add-update-menu";
    }

    @RequestMapping(value = "menu/added", method = RequestMethod.POST)
    public String saveOrUpdateMenu(@ModelAttribute("menuForm") Menu menu, final RedirectAttributes redirectAttributes) {
        LOGGER.debug("saveOrUpdateEmployee() is executed!");
        if (menu.isNew()) {
            menuService.addMenu(menu);
            redirectAttributes.addFlashAttribute("msg", "Меню " + menu.getName()
                    + " додане до бази даних");
        } else {
            menuService.updateMenu(menu);
            redirectAttributes.addFlashAttribute("msg", "Меню " + menu.getName()
                    + " оновлене у базі даних");

        }
        redirectAttributes.addFlashAttribute("css", "success");
        return "redirect:/";
    }

    @RequestMapping(value = "/menu/{id}/update", method = RequestMethod.GET)
    public String showUpdateMenuForm(@PathVariable("id") int id, Model model) {

        LOGGER.debug("showUpdateMenuForm() : {}", id);

        Menu menu = menuService.getMenuById(id);
        model.addAttribute("menuForm", menu);
        return "app.add-update-menu";
    }

    @RequestMapping(value = "menu/find", method = RequestMethod.GET)
    public String showFindMenuForm(Model model) {
        LOGGER.debug("showFindMenuForm() is executed!");
        model.addAttribute("findWhat", "MENU");
        return "app.find";
    }

    @RequestMapping(value = "menu/finded", method = RequestMethod.POST)
    public String showFindedMenuForm(@ModelAttribute("findString") String findString, Map<String, Object> model) {
        LOGGER.debug("showFindedMenuForm() is executed!", findString);
        model.put("menus", menuService.findMenuByName(findString));
        return "app.menus";
    }

    @RequestMapping(value = "menu/{id}/details", method = RequestMethod.GET)
    public String getMenuDetails(@PathVariable("id") int id, Model model){
        LOGGER.debug("getMenuDetails() is executed!");
        model.addAttribute("menu", menuService.getMenuById(id));
        model.addAttribute("dishes", menuService.getDishesFromMenu(id));
        model.addAttribute("newDishes", menuService.getNewDishes(id));
        model.addAttribute("newDish", new Dish(0, "Temp", 0, 0, 0));
        return "app.menu-details";
    }
    @RequestMapping(value = "/menu/{menuid}/dish/{dishid}/delete", method = RequestMethod.POST)
    public String deleteDishFromMenu(@PathVariable("menuid") int menuId, @PathVariable("dishid") int dishId,
                                     final RedirectAttributes redirectAttributes)
    {
        LOGGER.debug("deleteDishFromMenu() is executed!");
        menuService.removeDishFromMenu(dishId, menuId);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Страву видалено");

        return "redirect:/menu/" + menuId +"/details";
    }

     @RequestMapping(value = "/menu/{menuid}/dish/add", method = RequestMethod.POST)
    public String addDishToMenu(@PathVariable("menuid") int menuid,
                                @ModelAttribute("newDish") Dish newDish,
                                final RedirectAttributes redirectAttributes){
         LOGGER.debug("addDishToMenu() is executed!");
         menuService.addDishToMenu(newDish.getId(), menuid);

         redirectAttributes.addFlashAttribute("css", "success");
         redirectAttributes.addFlashAttribute("msg", "Страву додано");

         return "redirect:/menu/" + menuid +"/details";
     }

}

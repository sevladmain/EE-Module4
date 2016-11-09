package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.services.EmployeeService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SeVlad on 05.11.2016.
 */
@Controller
public class WebController {
    private final Logger LOGGER = LoggerFactory.getLogger(WebController.class);
    @Autowired
    private EmployeeService employeeService;

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Map<String, Object> model) {

        LOGGER.debug("default() is executed!");

        return "app.homepage";
    }

    @RequestMapping(value = "/employee/all", method = RequestMethod.GET)
    public String getAllEmployees(Map<String, Object> model) {

        LOGGER.debug("getAllEmployees() is executed!");
        model.put("employees", employeeService.getAllEmployees());
        return "app.employees";
    }

    @RequestMapping(value = "/employee/{id}/delete", method = RequestMethod.POST)
    public String deleteEmployee(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

        LOGGER.debug("deleteEmployee() is executed!");
        Employee employee = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(employee);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Працівник " + employee.getFirstName() + " " + employee.getLastName()
                + " видалений з бази даних");
        return "redirect:/employee/all";
    }

    @RequestMapping(value = "employee/add", method = RequestMethod.GET)
    public String showCreateEmployeeForm(Model model) {
        LOGGER.debug("showCreateEmployeeForm() is executed!");
        Employee employee = new Employee();

        model.addAttribute("employeeForm", employee);
        populatePostitions(model);
        return "app.add-update-employee";
    }

    private void populatePostitions(Model model) {
        List<Position> positions = employeeService.getAllPositions();
        Map<Integer, String> positionsList = new HashMap<>();
        for (Position position :
                positions) {
            positionsList.put(position.getId(), position.getPosition());
        }
        model.addAttribute("positionsList", positionsList);
    }

    @RequestMapping(value = "/employee/{id}/update", method = RequestMethod.GET)
    public String showUpdateEmployeeForm(@PathVariable("id") int id, Model model) {

        LOGGER.debug("showUpdateEmployeeForm() : {}", id);

        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employeeForm", employee);
        populatePostitions(model);
        return "app.add-update-employee";
    }

    @RequestMapping(value = "employee/added", method = RequestMethod.POST)
    public String saveOrUpdateEmployee(@ModelAttribute("employeeForm") Employee employee, final RedirectAttributes redirectAttributes) {
        LOGGER.debug("saveOrUpdateEmployee() is executed!");
        if (employee.isNew()) {
            employeeService.addEmployee(employee).getId();
            redirectAttributes.addFlashAttribute("msg", "Працівник " + employee.getFirstName() + " " + employee.getLastName()
                    + " доданий до бази даних");
        } else {
            employeeService.updateEmployee(employee);
            redirectAttributes.addFlashAttribute("msg", "Працівник " + employee.getFirstName() + " " + employee.getLastName()
                    + " оновлений у базі даних");

        }
        redirectAttributes.addFlashAttribute("css", "success");
        return "redirect:/";
    }

    @RequestMapping(value = "employee/find", method = RequestMethod.GET)
    public String showFindEmployeeForm(Model model) {
        LOGGER.debug("showFindEmployeeForm() is executed!");
        model.addAttribute("findWhat", "EMPLOYEE");
        populatePostitions(model);
        return "app.find";
    }

    @RequestMapping(value = "employee/finded", method = RequestMethod.POST)
    public String showFindedEmployeesForm(@ModelAttribute("findString") String findString, Map<String, Object> model) {
        LOGGER.debug("showFindedEmployeesForm() is executed!", findString);
        model.put("employees", employeeService.findEmployeeByName(findString));
        return "app.employees";
    }

}

package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.core.Category;
import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.services.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SeVlad on 05.11.2016.
 */
@Controller
public class WebController {
    private final Logger LOGGER = Logger.getLogger(WebController.class);
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

    @RequestMapping(value = "/delete-employee/{id}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable("id") int id, Map<String, Object> model) {

        LOGGER.debug("deleteEmployee() is executed!");
        Employee employee = employeeService.getEmployeeById(id);
        if(employeeService.deleteEmployee(employee) > 0){
            model.put("result", "Працівник " + employee.getFirstName() + " " + employee.getLastName()
                    + " видалений з бази даних");
        }else{
            model.put("result", "Працівник " + employee.getFirstName() + " " + employee.getLastName()
                    + "не може бути видалений з бази даних");
        }
        return "app.message";
    }

    @RequestMapping(value = "employee/add", method = RequestMethod.GET)
    public String showCreateEmployeeForm(Model model) {
        LOGGER.debug("showCreateEmployeeForm() is executed!");
        Employee employee = new Employee();

        model.addAttribute("employeeForm", employee);
        List<Position> positions = employeeService.getAllPositions();
        Map<Integer, String> positionsList = new HashMap<>();
        for (Position position:
             positions) {
            positionsList.put(position.getId(), position.getPosition());
        }
        model.addAttribute("positionsList", positionsList);
        return "app.add-employee";
    }

    @RequestMapping(value = "employee/added", method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute("employeeForm") Employee employee, Model model) {
        LOGGER.debug("saveEmployee() is executed!");
        employeeService.addEmployee(employee);
        model.addAttribute("result", "Працівник " + employee.getFirstName() + " " + employee.getLastName()
                + " доданий до бази даних");

        return "app.message";
    }

}

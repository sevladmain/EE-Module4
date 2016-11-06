package com.goit.homeworks.restaurant.web;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.services.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    @RequestMapping(value = "/get-all-employees", method = RequestMethod.GET)
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

}

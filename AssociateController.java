package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.Associate;
import com.utils.EmpRestURIConstants;


// import com.db.EmployeeEntity;
// import com.db.EmployeeRepository;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class AssociateController {

    /*
     * @Autowired private EmployeeRepository employeeRepository;
     */

    Map<Integer, Associate> empData = new HashMap<Integer, Associate>();

    /**
     * method to expose a hard code dummy employee
     * 
     * @return
     */
    @RequestMapping(value = EmpRestURIConstants.DUMMY_EMP, method = RequestMethod.GET)
    public @ResponseBody
    Associate getDummyEmployee() {
    	Associate emp = new Associate();
        emp.setId(9999);
        emp.setName("Dummy");
        emp.setCreatedDate(new Date());
        empData.put(9999, emp);
         
        return emp;
    }

    @RequestMapping(value = EmpRestURIConstants.GET_EMP, method = RequestMethod.GET)
    public @ResponseBody Associate getEmployee(@PathVariable("id") int empId) {           
        return empData.get(empId);
    }

   /* 
     * @RequestMapping(value = EmpRestURIConstants.GET_EMP, method =
     * RequestMethod.GET) public @ResponseBody Employee
     * getEmployee(@PathVariable("id") int empId) { List<EmployeeEntity>
     * empEnity = null; String modifiedEmpID = String.valueOf(empId); empEnity =
     * employeeRepository.findEmployeeById(modifiedEmpID); Employee employee =
     * new Employee(); for(EmployeeEntity emp : empEnity){
     * employee.setId(Integer.parseInt(emp.getEmpId()));
     * employee.setName(emp.getName()); employee.setCreatedDate(new Date()); }
     * return employee; }
     */

    /**
     * Method to get all the Employee details.
     * 
     * @return
     */
    @RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP, method = RequestMethod.GET)
    public @ResponseBody
    List<Associate> getAllEmployees() {
        List<Associate> emps = new ArrayList<Associate>();
        Set<Integer> empIdKeys = empData.keySet();
        for (Integer i : empIdKeys) {
            emps.add(empData.get(i));
        }
        return emps;
    }

    /**
     * Method to create a new employee.
     * 
     * @param emp
     * @return
     */
    @RequestMapping(value = EmpRestURIConstants.CREATE_EMP, method = RequestMethod.POST)
    public @ResponseBody
    Associate createEmployee(@RequestBody
    		Associate emp) {
        emp.setCreatedDate(new Date());
        empData.put(emp.getId(), emp);
        return emp;
    }

    /**
     * Method to delete an employee
     * 
     * @param empId
     * @return
     */
    @RequestMapping(value = EmpRestURIConstants.DELETE_EMP, method = RequestMethod.PUT)
    public @ResponseBody
    Associate deleteEmployee(@PathVariable("id")
    int empId) {
    	Associate emp = empData.get(empId);
        empData.remove(empId);
        return emp;
    }

}

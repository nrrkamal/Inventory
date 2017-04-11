package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.entity.Employee;
import com.domain.EmployeeDto;
import com.services.EmployeeService;
import com.utils.EmpRestURIConstants;

@RestController
public class EmployeeController extends MappingJackson2HttpMessageConverter {
	
    @Autowired
    private EmployeeService employeeService;
	
	 @RequestMapping(value = EmpRestURIConstants.GET_EMP_ID, method = RequestMethod.GET)
	    public @ResponseBody
	    EmployeeDto getEmployeeFromId(@PathVariable String employeeId) {	    	 
	    	Employee employee = employeeService.getEmployeeValueFromId(employeeId);
	    	EmployeeDto  emp = new EmployeeDto();
	    	emp.setEmployeeId(employee.getEmpId());
	    	emp.setEmployeeName(employee.getEmpName());
	    	emp.setEmployeeKlmId(employee.getKlmId());
	    	emp.setEmployeePresentStatus(employee.getEmpStatus());
	        return emp;
	    }	

	 
	  @RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP_DETAILS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody
	    List<EmployeeDto> getAllEmployeesAvailable() {	    	 
	    	List<Employee> employeeListFromDB = employeeService.getAllEmployeeValues();
	    	List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
	    	for(Employee employee : employeeListFromDB) {
	    		if (employee != null) {
	    			EmployeeDto  emp = new EmployeeDto();
	    	    	emp.setEmployeeId(employee.getEmpId());
	    	    	emp.setEmployeeName(employee.getEmpName());
	    	    	emp.setEmployeeKlmId(employee.getKlmId());
	    	    	emp.setEmployeePresentStatus(employee.getEmpStatus());	    	    	 
	    	    	employeeList.add(emp);
	    		}	    		 
	    	}
	        return employeeList;
	    }
	  
	    @RequestMapping(value = EmpRestURIConstants.ADD_EMP, method = RequestMethod.POST)
	    public @ResponseBody
	    EmployeeDto addEmployee(@RequestBody
	    		EmployeeDto emp) {
	        Employee employee = new Employee();
	        employee.setEmpId(emp.getEmployeeId());
	        employee.setEmpName(emp.getEmployeeName());
	        employee.setEmpStatus(emp.getEmployeePresentStatus());
	        employee.setKlmId(emp.getEmployeeKlmId());
	        employee = employeeService.addEmployeeDetails(employee);
	        return emp;
	    }
	    
	    @RequestMapping(value = EmpRestURIConstants.UPDATE_EMP, method = RequestMethod.PUT)
	    public @ResponseBody
	    EmployeeDto updateEmployee(@PathVariable String employeeId, @RequestBody
	    		EmployeeDto emp) {
	    	Employee employee = employeeService.getEmployeeValueFromId(employeeId);
	    	if (employee != null) {	    		
	            employee.setEmpStatus(emp.getEmployeePresentStatus());
	            employee.setKlmId(emp.getEmployeeKlmId());
	            employee = employeeService.updateUser(employee);
	            emp.setEmployeeId(employee.getEmpId());
    	    	emp.setEmployeeName(employee.getEmpName());
    	    	emp.setEmployeeKlmId(employee.getKlmId());
    	    	emp.setEmployeePresentStatus(employee.getEmpStatus());	 
	    	} else {
	    		return new EmployeeDto(HttpStatus.NO_CONTENT);
	    	}
	        return emp;
	    }
	    
	   /* @RequestMapping(value = EmpRestURIConstants.DELETE_EMP, method = RequestMethod.DELETE)
	    public @ResponseBody
	    void deleteEmployeeFromId(@PathVariable String employeeId) {    	 
	    	employeeService.deleteEmployeeFromId(employeeId);	    	
	    }*/
}
 
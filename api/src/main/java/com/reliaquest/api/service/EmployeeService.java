package com.reliaquest.api.service;

import com.reliaquest.api.model.EmployeeDto;
import com.reliaquest.api.model.EmployeeRequestDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();

    List<EmployeeDto> getEmployeesByNameSearch(String searchString);

    EmployeeDto getEmployeeById(String id);

    Integer getHighestSalary();

    List<String> getNamesOfHighestEarningEmployees(int numberOfEmployees);

    EmployeeDto createEmployee(EmployeeRequestDto employeeDto);

    String deleteEmployee(String id);
}

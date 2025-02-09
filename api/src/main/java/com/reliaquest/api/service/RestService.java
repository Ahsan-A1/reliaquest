package com.reliaquest.api.service;

import com.reliaquest.api.model.EmployeeDto;
import com.reliaquest.api.model.EmployeeRequestDto;

import java.util.List;

public interface RestService {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(String id);

    EmployeeDto createEmployee(EmployeeRequestDto employeeDto);

    Boolean deleteEmployee(String name);

}

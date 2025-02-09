package com.reliaquest.api.controller;

import com.reliaquest.api.model.EmployeeDto;
import com.reliaquest.api.model.EmployeeRequestDto;
import com.reliaquest.api.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class EmployeeController implements IEmployeeController<EmployeeDto, EmployeeRequestDto> {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDto> employees;
        try {
            employees = employeeService.getAllEmployees();
        } catch (Exception e) {
            log.error("Error while fetching all employees", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getEmployeesByNameSearch(String searchString) {
        log.info("Fetching employees by searchString: {} ", searchString);
        List<EmployeeDto> employees;
        try {
            employees = employeeService.getEmployeesByNameSearch(searchString);
        } catch (Exception e) {
            log.error("Error while fetching employees with name: {}", searchString, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(String id) {
        log.info("Fetching employee by id: {} ", id);
        EmployeeDto employee;
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (Exception e) {
            log.error("Error while fetching employee with id: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.info("Fetching highest salary of employees");
        Integer highestSalary;
        try {
            highestSalary = employeeService.getHighestSalary();
        } catch (Exception e) {
            log.error("Error while fetching highest salary of employees", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok(highestSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("Fetching top ten highest earning employee names");
        List<String> employeeNames;
        try {
            employeeNames = employeeService.getNamesOfHighestEarningEmployees(10);
        } catch (Exception e) {
            log.error("Error while fetching top ten highest earning employee names", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok(employeeNames);
    }

    @Override
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeRequestDto employeeInput) {
        log.info("Creating employee: {} ", employeeInput);
        EmployeeDto employee;
        try {
            employee = employeeService.createEmployee(employeeInput);
        } catch (Exception e) {
            log.error("Error while creating employee: {}", employeeInput, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        log.info("Deleting employee by id: {} ", id);
        String response;
        try {
            response = employeeService.deleteEmployee(id);
        } catch (Exception e) {
            log.error("Error while deleting employee with id: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}

package com.reliaquest.api.service.impl;

import com.reliaquest.api.TechnicalException;
import com.reliaquest.api.model.EmployeeDto;
import com.reliaquest.api.model.EmployeeRequestDto;
import com.reliaquest.api.service.EmployeeService;
import com.reliaquest.api.service.RestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final RestService restService;


    @Override
    public List<EmployeeDto> getAllEmployees() {
        return restService.getAllEmployees();
    }

    @Override
    public List<EmployeeDto> getEmployeesByNameSearch(String searchString) {

        if (searchString == null || searchString.isEmpty()) {
            log.error("Search string is empty");
            return getAllEmployees();
        }

        List<EmployeeDto> employees = restService.getAllEmployees();
        log.info("Fetched employee data");
        String finalSearchString = searchString.toLowerCase();

        return employees.stream()
                .filter(employee -> {
                    String employeeName = Optional.ofNullable(employee.getEmployeeName()).orElse("").toLowerCase();
                    return employeeName.contains(finalSearchString);
                })
                .toList();
    }

    @Override
    public EmployeeDto getEmployeeById(String id) {
        if (id == null || id.isEmpty()) {
            log.error("Employee id is empty");
            return new EmployeeDto();
        }
        return restService.getEmployeeById(id);
    }

    @Override
    public Integer getHighestSalary() {
        return getAllEmployees().stream().mapToInt(EmployeeDto::getEmployeeSalary).max().orElse(0);
    }

    @Override
    public List<String> getNamesOfHighestEarningEmployees(int numberOfEmployees) {
        List<EmployeeDto> employees = getAllEmployees();
        return employees.stream()
                .sorted((e1, e2) -> e2.getEmployeeSalary() - e1.getEmployeeSalary())
                .limit(numberOfEmployees)
                .map(EmployeeDto::getEmployeeName)
                .toList();
    }

    @Override
    public EmployeeDto createEmployee(EmployeeRequestDto employeeDto) {
        return restService.createEmployee(employeeDto);
    }

    @Override
    public String deleteEmployee(String id) {
        EmployeeDto employee = getEmployeeById(id);
        if (employee == null) {
            log.error("Employee not found with id: {}", id);
            throw new TechnicalException("Employee not found with id: " + id);
        } else if (!StringUtils.hasLength(employee.getEmployeeName())) {
            log.error("Employee name is empty");
            throw new TechnicalException("Employee name is empty");
        }
        restService.deleteEmployee(employee.getEmployeeName());
        return employee.getEmployeeName();
    }
}

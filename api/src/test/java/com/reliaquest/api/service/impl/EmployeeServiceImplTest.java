package com.reliaquest.api.service.impl;

import com.reliaquest.api.TechnicalException;
import com.reliaquest.api.model.EmployeeDto;
import com.reliaquest.api.model.EmployeeRequestDto;
import com.reliaquest.api.service.RestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private RestService restService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void getAllEmployees_fetchAll() {
        EmployeeDto employee1 = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        EmployeeDto employee2 = EmployeeDto.builder()
                .employeeName("bar")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        when(restService.getAllEmployees()).thenReturn(employees);

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        verify(restService, times(1)).getAllEmployees();
    }

    @Test
    void getAllEmployees_noEmployees() {
        when(restService.getAllEmployees()).thenReturn(new ArrayList<>());
        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertEquals(0, result.size());
        verify(restService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeesByNameSearch_emptySearchString() {
        EmployeeDto employee1 = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        EmployeeDto employee2 = EmployeeDto.builder()
                .employeeName("bar")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        when(restService.getAllEmployees()).thenReturn(employees);

        List<EmployeeDto> result = employeeService.getEmployeesByNameSearch("");

        assertEquals(2, result.size());
    }

    @Test
    void getEmployeesByNameSearch_nullForString() {
        EmployeeDto employee1 = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        EmployeeDto employee2 = EmployeeDto.builder()
                .employeeName("bar")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        when(restService.getAllEmployees()).thenReturn(employees);

        List<EmployeeDto> result = employeeService.getEmployeesByNameSearch(null);

        assertEquals(2, result.size());
    }

    @Test
    void getEmployeesByNameSearch_singleResult() {
        EmployeeDto employee1 = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        EmployeeDto employee2 = EmployeeDto.builder()
                .employeeName("bar")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        when(restService.getAllEmployees()).thenReturn(employees);

        List<EmployeeDto> result = employeeService.getEmployeesByNameSearch("foo");

        assertEquals(1, result.size());
        assertEquals("foo", result.get(0).getEmployeeName());
    }

    @Test
    void getEmployeesByNameSearch_multipleResult() {
        EmployeeDto employee1 = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        EmployeeDto employee2 = EmployeeDto.builder()
                .employeeName("bar")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        EmployeeDto employee3 = EmployeeDto.builder()
                .employeeName("baz")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2, employee3);
        when(restService.getAllEmployees()).thenReturn(employees);

        List<EmployeeDto> result = employeeService.getEmployeesByNameSearch("ba");

        assertEquals(2, result.size());
        List<String> names = result.stream().map(EmployeeDto::getEmployeeName).toList();
        assertTrue(names.contains("bar"));
        assertTrue(names.contains("baz"));
    }

    @Test
    void getEmployeeById_null() {
        EmployeeDto employee = new EmployeeDto();
        EmployeeDto result = employeeService.getEmployeeById(null);
        assertEquals(result, employee);
        verify(restService, times(0)).getEmployeeById(null);
    }

    @Test
    void getEmployeeById_empty() {
        EmployeeDto employee = new EmployeeDto();
        EmployeeDto result = employeeService.getEmployeeById("");
        assertEquals(result, employee);
        verify(restService, times(0)).getEmployeeById("null");
    }

    @Test
    void getEmployeeById_getEmployee() {
        EmployeeDto employee = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        when(restService.getEmployeeById(anyString())).thenReturn(employee);
        EmployeeDto result = employeeService.getEmployeeById("123");
        assertEquals(result, employee);
        verify(restService, times(1)).getEmployeeById("123");
    }

    @Test
    void getHighestSalary_noResult() {
        when(restService.getAllEmployees()).thenReturn(new ArrayList<>());

        int result = employeeService.getHighestSalary();

        assertEquals(0, result);
    }

    @Test
    void getHighestSalary_getHighest() {
        EmployeeDto employee1 = EmployeeDto.builder().employeeSalary(1).build();
        EmployeeDto employee2 = EmployeeDto.builder().employeeSalary(2).build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        when(restService.getAllEmployees()).thenReturn(employees);
        int result = employeeService.getHighestSalary();

        assertEquals(2, result);
    }

    @Test
    void getHighestSalary_singleResult() {
        EmployeeDto employee1 = EmployeeDto.builder().employeeSalary(1).build();
        List<EmployeeDto> employees = Arrays.asList(employee1);
        when(restService.getAllEmployees()).thenReturn(employees);
        int result = employeeService.getHighestSalary();

        assertEquals(1, result);
    }

    @Test
    void getHighestSalary_highestTie() {
        EmployeeDto employee1 = EmployeeDto.builder().employeeSalary(1).build();
        EmployeeDto employee2 = EmployeeDto.builder().employeeSalary(1).build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        when(restService.getAllEmployees()).thenReturn(employees);
        int result = employeeService.getHighestSalary();

        assertEquals(1, result);
    }

    @Test
    void getNamesOfHighestEarningEmployees_noResult() {
        List<EmployeeDto> employees = new ArrayList<>();
        when(restService.getAllEmployees()).thenReturn(employees);

        List<String> result = employeeService.getNamesOfHighestEarningEmployees(1);

        assertEquals(0, result.size());
    }

    @Test
    void getNamesOfHighestEarningEmployees_numberUnderLimit() {
        EmployeeDto employee1 = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        List<EmployeeDto> employees = Arrays.asList(employee1);
        when(restService.getAllEmployees()).thenReturn(employees);

        List<String> result = employeeService.getNamesOfHighestEarningEmployees(2);

        assertEquals(1, result.size());
        assertEquals("foo", result.get(0));
    }

    @Test
    void getNamesOfHighestEarningEmployees_numberOverLimit() {
        EmployeeDto employee1 = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        EmployeeDto employee2 = EmployeeDto.builder()
                .employeeName("bar")
                .employeeAge(11)
                .employeeSalary(2000)
                .build();
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        when(restService.getAllEmployees()).thenReturn(employees);

        List<String> result = employeeService.getNamesOfHighestEarningEmployees(1);

        assertEquals(1, result.size());
        assertEquals("bar", result.get(0));
    }

    @Test
    void createEmployee_create() {
        EmployeeRequestDto requestDto = EmployeeRequestDto.builder()
                .name("foo")
                .age(11)
                .salary(1000)
                .build();
        EmployeeDto responseDto = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        when(restService.createEmployee(requestDto)).thenReturn(responseDto);

        EmployeeDto result = employeeService.createEmployee(requestDto);

        assertEquals(responseDto, result);
        verify(restService, times(1)).createEmployee(requestDto);
    }

    @Test
    void deleteEmployee_notFoundNull() {
        assertThrows(TechnicalException.class, () -> employeeService.deleteEmployee(null));
    }

    @Test
    void deleteEmployee_notFoundEmptyId() {
        assertThrows(TechnicalException.class, () -> employeeService.deleteEmployee(""));
    }

    @Test
    void deleteEmployee_deleteSuccess() {
        EmployeeDto employee = EmployeeDto.builder()
                .employeeName("foo")
                .employeeAge(11)
                .employeeSalary(1000)
                .build();
        when(restService.getEmployeeById("123")).thenReturn(employee);
        when(restService.deleteEmployee("foo")).thenReturn(true);

        String result = employeeService.deleteEmployee("123");

        assertEquals("foo", result);
        verify(restService, times(1)).getEmployeeById("123");
        verify(restService, times(1)).deleteEmployee("foo");
    }

}
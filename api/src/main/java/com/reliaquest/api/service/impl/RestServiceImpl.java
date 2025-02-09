package com.reliaquest.api.service.impl;

import com.reliaquest.api.TechnicalException;
import com.reliaquest.api.model.EmployeeDeleteRequestDto;
import com.reliaquest.api.model.EmployeeDto;
import com.reliaquest.api.model.EmployeeRequestDto;
import com.reliaquest.api.model.ResponseDto;
import com.reliaquest.api.service.RestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class RestServiceImpl implements RestService {

    private final RestTemplate restTemplate;
    private final String RELIAQUEST_SERVER_BASE_PATH = "http://localhost:8112/api/v1/";
    //@Value("${reliaquest.server.base-path}")
    // private String RELIAQUEST_SERVER_BASE_PATH;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        ResponseEntity<ResponseDto<List<EmployeeDto>>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    RELIAQUEST_SERVER_BASE_PATH + "/employee",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            log.info("Employee Api Called with status {}", Objects.requireNonNull(responseEntity.getBody()).getStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TechnicalException("Error while calling the get employee api");
        }
        return responseEntity.getBody().getData();
    }

    @Override
    public EmployeeDto getEmployeeById(String id) {
        ResponseEntity<ResponseDto<EmployeeDto>> responseEntity;
        try {

            String url = UriComponentsBuilder.fromHttpUrl(RELIAQUEST_SERVER_BASE_PATH)
                    .pathSegment("employee", id)
                    .toUriString();

            responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            log.info("Employee Api Called with status {}", Objects.requireNonNull(responseEntity.getBody()).getStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TechnicalException("Error while calling the get employee api");
        }
        return responseEntity.getBody().getData();
    }

    @Override
    public EmployeeDto createEmployee(EmployeeRequestDto employeeDto) {
        ResponseEntity<ResponseDto<EmployeeDto>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    RELIAQUEST_SERVER_BASE_PATH + "/employee",
                    HttpMethod.POST,
                    new HttpEntity<>(employeeDto),
                    new ParameterizedTypeReference<>() {
                    }
            );
            log.info("Create employee with status {}", Objects.requireNonNull(responseEntity.getBody()).getStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TechnicalException("Error while calling the create employee api");
        }
        return responseEntity.getBody().getData();
    }

    @Override
    public Boolean deleteEmployee(String name) {
        ResponseEntity<ResponseDto<Boolean>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    RELIAQUEST_SERVER_BASE_PATH + "/employee",
                    HttpMethod.DELETE,
                    new HttpEntity<>(EmployeeDeleteRequestDto.builder().name(name).build()),
                    new ParameterizedTypeReference<>() {
                    }
            );
            log.info("Delete employee with status {}", Objects.requireNonNull(responseEntity.getBody()).getStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TechnicalException("Error while calling the delete employee api");
        }
        return responseEntity.getBody().getData();
    }


}

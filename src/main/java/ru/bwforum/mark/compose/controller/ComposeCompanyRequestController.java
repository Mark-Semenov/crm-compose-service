package ru.bwforum.mark.compose.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;
import ru.bwforum.mark.compose.service.CompanyService;
import ru.bwforum.mark.compose.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ComposeCompanyRequestController {

    private final CompanyService companyService;
    private final CustomerService customerService;

    @GetMapping("/get/company/{id}")
    public Mono<ResponseEntity<CompanyDTO>> getCompanyWithCustomer(@PathVariable(name = "id") String id) {
        return companyService
                .getReadyDto(id)
                .map(ResponseEntity::ofNullable);
    }

    @GetMapping("/get/customer/{id}")
    public Mono<ResponseEntity<CustomerDTO>> getCustomerWithCompany(@PathVariable(name = "id") String id) {
        return customerService
                .getReadyDto(id)
                .map(ResponseEntity::ofNullable);
    }


}

package ru.bwforum.mark.compose.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;
import ru.bwforum.mark.compose.service.CompanyService;
import ru.bwforum.mark.compose.service.CustomerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ComposeCompanyRequestController {

    private final CompanyService companyService;
    private final CustomerService customerService;

    @GetMapping("/get/company/{id}")
    public Mono<ResponseEntity<CompanyDTO>> getCompanyWithCustomer(
            @PathVariable(name = "id") String id,
            @AuthenticationPrincipal Jwt jwt) {
        return companyService
                .getReadyDto(id, jwt.getTokenValue())
                .map(ResponseEntity::ofNullable);
    }

    @GetMapping("/get/customer/{id}")
    public Mono<ResponseEntity<CustomerDTO>> getCustomerWithCompany(
            @PathVariable(name = "id") String id,
            @AuthenticationPrincipal Jwt jwt) {
        return customerService
                .getReadyDto(id, jwt.getTokenValue())
                .map(ResponseEntity::ofNullable);
    }


}

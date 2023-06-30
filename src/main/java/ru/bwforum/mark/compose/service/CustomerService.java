package ru.bwforum.mark.compose.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    Mono<CustomerDTO> getReadyDto(String customerId, String token);

    Mono<CustomerDTO> buildCustomerDto(String id, String token);

    Mono<CustomerDTO> getCustomerDtoById(String url, String id, String token);

    Flux<CompanyDTO> getCompaniesDtoByCustomerId(String url, String id, String token);

    CustomerDTO putCompaniesToCustomer(CustomerDTO customer, List<CompanyDTO> companies);

}

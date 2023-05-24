package ru.bwforum.mark.compose.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    Mono<CustomerDTO> getReadyDto(String customerId);

    Mono<CustomerDTO> buildCustomerDto(String id);

    Mono<CustomerDTO> getCustomerDtoById(String url, String id);

    Flux<CompanyDTO> getCompaniesDtoByCustomerId(String url, String id);

    CustomerDTO putCompaniesToCustomer(CustomerDTO customer, List<CompanyDTO> companies);

}

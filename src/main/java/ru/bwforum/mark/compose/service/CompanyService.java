package ru.bwforum.mark.compose.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;

import java.util.List;

public interface CompanyService {

    Mono<CompanyDTO> getReadyDto(String companyId, String token);

    Mono<CompanyDTO> buildCompanyDto(String id, String token);

    Mono<CompanyDTO> getCompanyDtoById(String url, String id, String token);

    Flux<CustomerDTO> getCustomersDtoByCompanyId(String url, String id, String token);

    CompanyDTO putCustomersToCompany(CompanyDTO company, List<CustomerDTO> customers);

}

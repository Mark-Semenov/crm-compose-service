package ru.bwforum.mark.compose.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;

import java.util.List;

public interface CompanyService {

    Mono<CompanyDTO> getReadyDto(String companyId);

    Mono<CompanyDTO> buildCompanyDto(String id);

    Mono<CompanyDTO> getCompanyDtoById(String url, String id);

    Flux<CustomerDTO> getCustomersDtoByCompanyId(String url, String id);

    CompanyDTO putCustomersToCompany(CompanyDTO company, List<CustomerDTO> customers);

}

package ru.bwforum.mark.compose.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;
import ru.bwforum.mark.compose.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

import static ru.bwforum.mark.compose.util.Path.GET_COMPANY_BY_ID;
import static ru.bwforum.mark.compose.util.Path.GET_CUSTOMERS_BY_COMPANY_ID;
import static ru.bwforum.mark.compose.util.Utils.createUrl;

@Component
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    @Value("${crm.queue.customerUrl}")
    public String customerUrl;

    @Value("${crm.queue.companyUrl}")
    public String companyUrl;

    private final WebClient webClient;

    @Override
    public Mono<CompanyDTO> getReadyDto(String companyId) {
        return buildCompanyDto(companyId);
    }


    @Override
    public Mono<CompanyDTO> buildCompanyDto(String id) {
        return getCompanyDtoById(createUrl(companyUrl, GET_COMPANY_BY_ID.path), id)
                .zipWith(getCustomersDtoByCompanyId(createUrl(customerUrl, GET_CUSTOMERS_BY_COMPANY_ID.path), id)
                        .collectList(), this::putCustomersToCompany);
    }

    @Override
    public Mono<CompanyDTO> getCompanyDtoById(String url, String id) {
        return webClient
                .get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(CompanyDTO.class);
    }

    @Override
    public Flux<CustomerDTO> getCustomersDtoByCompanyId(String url, String id) {
        return webClient
                .get()
                .uri(url, id)
                .retrieve()
                .bodyToFlux(CustomerDTO.class);
    }

    @Override
    public CompanyDTO putCustomersToCompany(CompanyDTO company, List<CustomerDTO> customers) {
        company.setCustomerDTO(new ArrayList<>());
        for (CustomerDTO dto : customers) {
            company.getCustomerDTO().add(dto);
        }
        return company;
    }

}
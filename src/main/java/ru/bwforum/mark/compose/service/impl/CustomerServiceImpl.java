package ru.bwforum.mark.compose.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;
import ru.bwforum.mark.compose.dto.CustomerDTO;
import ru.bwforum.mark.compose.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

import static ru.bwforum.mark.compose.util.Path.GET_COMPANIES_BY_CUSTOMER_ID;
import static ru.bwforum.mark.compose.util.Path.GET_CUSTOMER_BY_ID;
import static ru.bwforum.mark.compose.util.Utils.createUrl;

@Component
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Value("${crm.queue.customerUrl}")
    public String customerUrl;

    @Value("${crm.queue.companyUrl}")
    public String companyUrl;
    private final WebClient webClient;

    @Override
    public Mono<CustomerDTO> getReadyDto(String customerId, String token) {
        return buildCustomerDto(customerId, token);
    }

    @Override
    public Mono<CustomerDTO> buildCustomerDto(String id, String token) {

        return getCustomerDtoById(createUrl(customerUrl, GET_CUSTOMER_BY_ID.path), id,token)
                .zipWith(getCompaniesDtoByCustomerId(createUrl(companyUrl, GET_COMPANIES_BY_CUSTOMER_ID.path), id, token)
                        .collectList(), this::putCompaniesToCustomer);
    }

    @Override
    public Mono<CustomerDTO> getCustomerDtoById(String url, String id, String token) {
        return webClient
                .get()
                .uri(url, id)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(CustomerDTO.class);
    }


    @Override
    public Flux<CompanyDTO> getCompaniesDtoByCustomerId(String url, String id, String token) {
        return webClient
                .get()
                .uri(url, id)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(CompanyDTO.class);
    }

    @Override
    public CustomerDTO putCompaniesToCustomer(CustomerDTO customer, List<CompanyDTO> companies) {
        customer.setCompanyDTO(new ArrayList<>());
        for (CompanyDTO dto : companies) {
            customer.getCompanyDTO().add(dto);
        }

        return customer;
    }


}

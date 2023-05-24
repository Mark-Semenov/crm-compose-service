package ru.bwforum.mark.compose.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CustomerDTO;

import static ru.bwforum.mark.compose.util.Path.BIND_CUSTOMER;
import static ru.bwforum.mark.compose.util.Path.UNBIND_CUSTOMER;
import static ru.bwforum.mark.compose.util.Utils.createUrl;

@Log4j2
@Component
@RequiredArgsConstructor
public class CompanyQueueHandler implements QueueHandlerService {


    @Value("${crm.queue.customerUrl}")
    public String customerUrl;

    @Value("${crm.queue.companyUrl}")
    public String companyUrl;
    private final WebClient webClient;


    @RabbitListener(queues = "customer_queue")
    public Mono<Void> queueListener(CustomerDTO customerDto) {
        return queueHandler(customerDto);
    }

    @Override
    public <T> Mono<Void> queueHandler(T customerDto) {
        log.info("RECEIVED CUSTOMER: {}", customerDto.toString());

        CustomerDTO dto = (CustomerDTO) customerDto;

        if (dto.getId() == null || dto.getUniqueCompaniesId().isEmpty()) {
            return Mono.empty();
        }

        if (dto.isDelete()) {
            return unbind(dto.getId()).log();
        }

        return bind(customerDto).log();
    }


    @Override
    public Mono<Void> unbind(String customerId) {
        return webClient
                .get()
                .uri(createUrl(companyUrl, UNBIND_CUSTOMER.path), customerId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public <T> Mono<Void> bind(T customerDto) {
        return webClient
                .patch()
                .uri(createUrl(companyUrl, BIND_CUSTOMER.path))
                .body(Mono.just(customerDto), CustomerDTO.class)
                .retrieve()
                .bodyToMono(Void.class);
    }


}

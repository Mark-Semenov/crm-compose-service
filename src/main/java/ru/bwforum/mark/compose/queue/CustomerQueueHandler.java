package ru.bwforum.mark.compose.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bwforum.mark.compose.dto.CompanyDTO;

import static ru.bwforum.mark.compose.util.Path.BIND_COMPANY;
import static ru.bwforum.mark.compose.util.Path.UNBIND_COMPANY;
import static ru.bwforum.mark.compose.util.Utils.createUrl;


@Component
@RequiredArgsConstructor
public class CustomerQueueHandler implements QueueHandlerService {

    @Value("${crm.queue.customerUrl}")
    public String customerUrl;

    @Value("${crm.queue.companyUrl}")
    public String companyUrl;
    private final WebClient webClient;

    @RabbitListener(queues = "company_queue")
    public Mono<Void> queueListener(CompanyDTO company) {
        return queueHandler(company);
    }

    @Override
    public <T> Mono<Void> queueHandler(T companyDto) {

        CompanyDTO dto = (CompanyDTO) companyDto;
        if (dto.getId() == null || dto.getUniqueCustomersId().isEmpty()) {
            return Mono.empty();
        }

        if (dto.isDelete()) {
            return unbind(dto.getId()).log();
        }

        return bind(companyDto).log();
    }

    @Override
    public <T> Mono<Void> bind(T companyDto) {
        return webClient
                .patch()
                .uri(createUrl(customerUrl, BIND_COMPANY.path))
                .body(Mono.just((CompanyDTO) companyDto), CompanyDTO.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> unbind(String companyId) {
        return webClient
                .get()
                .uri(createUrl(customerUrl, UNBIND_COMPANY.path), companyId)
                .retrieve()
                .bodyToMono(Void.class);
    }

}

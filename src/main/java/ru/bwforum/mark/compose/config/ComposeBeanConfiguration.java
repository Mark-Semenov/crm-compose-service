package ru.bwforum.mark.compose.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ComposeBeanConfiguration {

    @Value("${crm.queue.baseUrl}")
    public String baseUrl;

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create(baseUrl);
    }


}

package ru.bwforum.mark.compose.queue;

import reactor.core.publisher.Mono;

public interface QueueHandlerService {

    Mono<Void> unbind(String id);

    <T> Mono<Void> bind(T dto);

    <T> Mono<Void> queueHandler(T dto);

}

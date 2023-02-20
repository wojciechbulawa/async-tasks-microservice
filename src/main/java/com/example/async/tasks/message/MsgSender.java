package com.example.async.tasks.message;

import com.example.async.tasks.config.rabbit.Exchanges;
import com.example.async.tasks.config.rabbit.Queues;
import com.example.async.tasks.dto.HelloMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MsgSender {

    private final RabbitTemplate rabbitTemplate;

    public void send(HelloMessage msg) {
        send(msg, Exchanges.TOPIC, Queues.MSG_ROUTING_KEY);
    }

    public void sendAsAdmin(HelloMessage msg) {
        send(msg, Exchanges.TOPIC, Queues.MSG_ROUTING_KEY + ".admin");
    }

    private void send(Object msg, String exchange, String routingKey) {
        log.info("Sending \"{}\" using {} with {}", msg, exchange, routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }
}

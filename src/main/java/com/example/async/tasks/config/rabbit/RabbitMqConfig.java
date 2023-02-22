package com.example.async.tasks.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitMqConfig {

    //region exchanges

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(Exchanges.TOPIC);
    }

    //endregion

    //region queues

    @Bean
    Queue msgQueue() {
        return new Queue(Queues.MSG, true);
    }

    @Bean
    Queue adminMsgQueue() {
        return new Queue(Queues.MSG_ADMIN, true);
    }

    @Bean
    Queue tasksMsgQueue() {
        return new Queue(Queues.TASKS, true);
    }

    //endregion

    //region bindings

    @Bean
    Binding adminMsgBinding(Queue adminMsgQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(adminMsgQueue)
                .to(topicExchange)
                .with(Queues.MSG_ROUTING_KEY + ".#");
    }

    @Bean
    Binding msgBinding(Queue msgQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(msgQueue)
                .to(topicExchange)
                .with(Queues.MSG_ROUTING_KEY);
    }

    @Bean
    Binding tasksBinding(Queue tasksMsgQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(tasksMsgQueue)
                .to(topicExchange)
                .with(Queues.TASKS_ROUTING_KEY);
    }

    //endregion
}

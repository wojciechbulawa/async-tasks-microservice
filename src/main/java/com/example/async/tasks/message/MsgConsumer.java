package com.example.async.tasks.message;

import com.example.async.tasks.config.rabbit.Queues;
import com.example.async.tasks.dto.HelloMessage;
import com.example.async.tasks.service.ProcessingService;
import com.example.async.tasks.service.ProcessingTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MsgConsumer {

    private final ProcessingService processingService;

    @RabbitListener(queues = Queues.MSG)
    public void receiveUserMessages(Message message, HelloMessage msg) {
        log(Queues.MSG, message, msg);
    }

    @RabbitListener(queues = Queues.MSG_ADMIN)
    public void receiveAdminMessages(Message message, HelloMessage msg) {
        log(Queues.MSG_ADMIN, message, msg);
    }

    @RabbitListener(queues = Queues.TASKS)
    public void receiveUserMessages(Message message, ProcessingTask task) {
        log(Queues.TASKS, message, task);
        processingService.process(task);
    }

    private void log(String queue, Message message, Object object) {
        log.info(
                """

                        === {} Receiver ==
                        Received a message: "{}"
                        Properties: {}
                        """, queue, object, message.getMessageProperties());
    }
}

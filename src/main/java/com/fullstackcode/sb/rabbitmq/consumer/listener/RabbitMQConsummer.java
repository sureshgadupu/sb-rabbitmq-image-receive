package com.fullstackcode.sb.rabbitmq.consumer.listener;

import com.fullstackcode.sb.rabbitmq.consumer.model.Event;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.JsonbMessageConverter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsummer {

    @RabbitListener(queues = "queue.A")
    private void receiveQueueA(Event event) {
       log.info("Event received from queue A -> {}",event.toString());

    }

    @RabbitListener(queues = "queue.B")
    private void receiveQueueB(Message event) {
        log.info("Event received from queue A -> {}",event.toString());
    }

    @RabbitListener(queues = "queue.C")
    private void receiveQueueC(Message event) {
        log.info("Event received from queue A -> {}",event.toString());
    }


}

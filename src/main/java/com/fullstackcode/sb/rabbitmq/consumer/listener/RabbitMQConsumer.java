package com.fullstackcode.sb.rabbitmq.consumer.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackcode.sb.rabbitmq.consumer.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class RabbitMQConsumer {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitTemplate jsonRabbitTemplate;

    @RabbitListener(queues = "queue.images")
    private void receiveImages(Message message)  {
        MessageProperties messageProperties = message.getMessageProperties();
        String fileName = messageProperties.getHeader("name");
        String extension = messageProperties.getHeader("extension");
        Path path = Paths.get("E://rabbitmq//"+fileName+"."+extension);

        try {
            Files.write(path, message.getBody() );
         } catch(IOException e) {
            log.error("Error writing file (queue.images)-> {}",e);
        }
       log.info("Event received from queue.images -> {}",message.toString());

    }

    @RabbitListener(queues = "queue.docs")
    private void receiveDocs(Message message) {
        MessageProperties messageProperties = message.getMessageProperties();
        String fileName = messageProperties.getHeader("name");
        String extension = messageProperties.getHeader("extension");
        Path path = Paths.get("E://rabbitmq//"+fileName+"."+extension);

        try {
            Files.write(path, message.getBody() );
        } catch(IOException e) {
            log.error("Error writing file (queue.docs) -> {}",e);
        }
        log.info("Event received from queue.docs  -> {}",message.toString());
    }

    @RabbitListener(queues = "queue.messages")
    private void receiveQueueC(Message message) {
        log.info("Event received from queue.messages-> {}", new String(message.getBody(), StandardCharsets.UTF_8));
    }

    @RabbitListener(queues = "queue.jsonmessages")
    private void receiveQueueJsonMessages(Message message) {
        ObjectMapper mapper = new ObjectMapper();
        Event event;
        try {
           event =  mapper.readValue(message.getBody(),Event.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Event received from queue.jsonmessages -> {} , {}", event.getName(), event.getId());

    }


}

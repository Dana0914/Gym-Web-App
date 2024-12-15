package epam.com.gymapplication.jms;

import jakarta.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TrainerTrainingSessionProducer {

    private static final Logger logger = LoggerFactory.getLogger(TrainerTrainingSessionProducer.class);
    private final JmsTemplate jmsTemplate;

    private final Queue queue;

    public TrainerTrainingSessionProducer(JmsTemplate jmsTemplate, Queue queue) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }


    public void sendMessage(String message) {
        try {
            logger.info("Attempting Send message: {}", message);
            jmsTemplate.convertAndSend(queue, message);
            logger.info("Sent Message to Queue: {}", queue);
        } catch (Exception e){
            logger.error("Received Exception during send Message: ", e);
        }
    }


}

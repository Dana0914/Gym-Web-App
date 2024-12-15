package epam.com.gymapplication.jms;



import com.fasterxml.jackson.databind.ObjectMapper;
import epam.com.gymapplication.dto.TrainingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProducerMessageController {

    private final TrainerTrainingSessionProducer producer;
    private static final Logger logger = LoggerFactory.getLogger(ProducerMessageController.class);
    private final ObjectMapper mapper;

    public ProducerMessageController(TrainerTrainingSessionProducer producer, ObjectMapper mapper) {
        this.producer = producer;
        this.mapper = mapper;
    }


    @PostMapping("/sendMessage")
    public String publishMessage(@Validated(
            {TrainingDTO.AddTrainersTrainingWorkload.class})
            @RequestBody TrainingDTO trainingDTO) {

        try {
            String trainingDTOAsJson = mapper.writeValueAsString(trainingDTO);
            producer.sendMessage(trainingDTOAsJson);
            logger.info("Message sent {}, trainingDTO: {}", trainingDTOAsJson, trainingDTO);
        } catch (Exception e) {
            logger.error("Error while sending message {}", e.getMessage());
        }
        return "Message was sent successfully";
    }
}

package epam.com.gymapplication.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Trainee.class, name = "trainee"),
        @JsonSubTypes.Type(value = Trainer.class, name = "trainer"),
        @JsonSubTypes.Type(value = Training.class, name = "training")
})


public abstract class UserBase {

}

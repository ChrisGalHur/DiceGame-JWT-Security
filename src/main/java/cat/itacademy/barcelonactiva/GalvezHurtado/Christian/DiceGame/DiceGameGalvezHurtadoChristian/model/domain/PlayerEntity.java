package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Players")
public class PlayerEntity {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "registration")
    private Date registration;
    @Field(name = "history")
    private List<DataPlayerEntity> dataPlayer;
}

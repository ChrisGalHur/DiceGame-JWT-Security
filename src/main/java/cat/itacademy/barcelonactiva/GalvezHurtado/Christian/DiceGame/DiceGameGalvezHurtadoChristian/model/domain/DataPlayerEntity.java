package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Players")
public class DataPlayerEntity {

    @Id
    @Field(name = "id")
    private String id;
    @Field(name = "Dice 1")
    private int numDice1;
    @Field(name = "Dice 2")
    private int numDice2;
    @Field(name = "result")
    private String result;

    public DataPlayerEntity(int numDice1, int numDice2, String result) {
        this.numDice1 = numDice1;
        this.numDice2 = numDice2;
        this.result = result;
    }
}

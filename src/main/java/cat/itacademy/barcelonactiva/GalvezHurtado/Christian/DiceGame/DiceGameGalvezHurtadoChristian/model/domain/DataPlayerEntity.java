package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Players")
public class DataPlayerEntity {

    @Id
    private String idData;//Cambiados todos los id a String

    private int numDice1;

    private int numDice2;

    private boolean win;

    private PlayerEntity playerEntity;

    public DataPlayerEntity(int numDice1, int numDice2, boolean win, PlayerEntity playerEntity) {
        this.numDice1 = numDice1;
        this.numDice2 = numDice2;
        this.win = win;
        this.playerEntity = playerEntity;
    }
}

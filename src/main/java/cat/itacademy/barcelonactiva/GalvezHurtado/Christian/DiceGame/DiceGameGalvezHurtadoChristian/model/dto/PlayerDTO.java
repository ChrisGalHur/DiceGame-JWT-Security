package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private String id;
    private String name;
    private Date registration = Timestamp.from(Instant.now());
    private String password;
    private List<DataPlayerEntity> dataPlayer = new ArrayList<>();
    private double percentage;

    public PlayerDTO(String name, double percent) {
        this.name = name;
        this.percentage = percent;
    }
}

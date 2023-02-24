package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data //Getters y Setters
@AllArgsConstructor //Constructor utilizando todos los argumentos
@NoArgsConstructor //Constructor sin argumentos
@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "Name")
    private String namePlayer;

    //@DateTimeFormat(pattern = "dd - mm - yyyy")
    @Column(name = "Registration Date")
    private Timestamp registrationDate;

    @OneToMany(mappedBy = "playerEntity", cascade = CascadeType.ALL)
    //@JoinColumn(name = "recount_id", referencedColumnName = "id")
    private List<DataPlayerEntity> dataPlayer = new ArrayList<>();

}

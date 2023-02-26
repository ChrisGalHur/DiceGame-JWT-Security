package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*@NamedQueries({
        @NamedQuery(
                name = "PlayerEntity.findWinner",
                query = "SELECT players.name as winner FROM PlayerEntity INNER JOIN recount ON players.id = recount.players_id WHERE recount.win = 1 GROUP BY players.id ORDER BY SUM(recount.win) DESC LIMIT 1"
        )
})*/
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

    @Column(name = "name")
    private String name;

    //@DateTimeFormat(pattern = "dd - mm - yyyy")
    @Column(name = "registration")
    private Timestamp registrationDate;

    @OneToMany(mappedBy = "playerEntity", cascade = CascadeType.ALL)
    //@JoinColumn(name = "recount_id", referencedColumnName = "id")
    private List<DataPlayerEntity> dataPlayer = new ArrayList<>();
}

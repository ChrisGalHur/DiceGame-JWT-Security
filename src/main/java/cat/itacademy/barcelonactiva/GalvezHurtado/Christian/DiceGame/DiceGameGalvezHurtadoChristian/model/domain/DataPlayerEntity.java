package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recount")
public class DataPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "Dice1")
    private int numDice1;

    @Column(name = "Dice2")
    private int numDice2;

    @Column(name = "win", columnDefinition = "TINYINT(1)")
    private boolean win;

    //@OneToOne(mappedBy = "dataPlayerEntity")
    @ManyToOne
    @JoinColumn(name = "players_id"/*, referencedColumnName = "id"*/)
    private PlayerEntity playerEntity;

    public DataPlayerEntity(int numDice1, int numDice2, boolean win, PlayerEntity playerEntity) {
        this.numDice1 = numDice1;
        this.numDice2 = numDice2;
        this.win = win;
        this.playerEntity = playerEntity;
    }
}

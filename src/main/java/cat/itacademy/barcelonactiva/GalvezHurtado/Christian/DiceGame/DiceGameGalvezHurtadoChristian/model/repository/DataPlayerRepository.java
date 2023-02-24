package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataPlayerRepository extends JpaRepository<DataPlayerEntity, Integer> {

    @Query("SELECT players.name as winner FROM players INNER JOIN recount ON players.id = recount.players_id WHERE recount.win = 1 GROUP BY players.id ORDER BY SUM(recount.win) DESC LIMIT 1;")
    Optional<PlayerEntity> findWinner();
}

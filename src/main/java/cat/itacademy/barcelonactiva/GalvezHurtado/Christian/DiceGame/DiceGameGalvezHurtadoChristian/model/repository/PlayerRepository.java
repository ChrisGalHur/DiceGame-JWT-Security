package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {

    @Query(value = "SELECT players.name FROM players INNER JOIN recount ON players.id = recount.players_id WHERE recount.win = 1 GROUP BY players.id ORDER BY SUM(recount.win) DESC LIMIT 1",
            nativeQuery = true)
    Optional<List<Object>> findByWinner();

    @Query(value = "SELECT players.name FROM players INNER JOIN recount ON players.id = recount.players_id WHERE recount.win = 1 GROUP BY players.id ORDER BY SUM(recount.win) ASC LIMIT 1",
            nativeQuery = true)
    Optional<List<Object>> findByLoser();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM recount WHERE players_id = ?1",
            nativeQuery = true)
    void deleteHistoryPlayer(Integer idToDeleteHistory);

    @Query(value = "SELECT recount.dice1, recount.dice2, recount.win FROM recount WHERE players_id = ?1",
    nativeQuery = true)
    Optional<List<Object>> findHistoryPlayer(int idPlayer);

    @Query(value = "SELECT players.name, ROUND(AVG(recount.win) * 100, 2) AS win_percentage FROM dicegame.players JOIN dicegame.recount ON players.id = recount.players_id GROUP BY players.id",
            nativeQuery = true)
    Optional<List<Object>> findAllWithPercentage();

    @Query(value = "SELECT ROUND(SUM(recount.win) / COUNT(*) * 100, 2) AS total_win_percentage FROM dicegame.players JOIN dicegame.recount ON players.id = recount.players_id;",
            nativeQuery = true)
    Optional<Object> getTotalAverage();
}

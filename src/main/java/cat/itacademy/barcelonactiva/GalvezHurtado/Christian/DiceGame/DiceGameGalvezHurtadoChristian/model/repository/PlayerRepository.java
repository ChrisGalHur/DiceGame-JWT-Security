package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {

    /*@Query("select players.name inner join recount on players.id = recount.players_id")
    String getLoser();


    @Query()
    String getWinner();*/

    @Query("SELECT p FROM PlayerEntity p JOIN p.recounts r WHERE r.win = true GROUP BY p.id ORDER BY COUNT(r) DESC")
    Optional<PlayerEntity> findWinner();

    /*PlayerEntity findByName (String name);*/
}

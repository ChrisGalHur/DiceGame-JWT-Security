package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {

    boolean existsById(String idPlayer);

    Optional<PlayerEntity> findById(String idPlayer);

    //List<String> findNamePlayerByDataPlayerResultOrderByDataPlayerResultDesc();
}

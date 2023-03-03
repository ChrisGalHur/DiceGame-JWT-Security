package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerEntity, Integer> {

    boolean existsByIdPlayer(String idPlayer);

    Object findByIdPlayer(ObjectId idPlayer);

    /*Object findNameByDataPlayerEntityFindByTopByOrderByWinDesc();*/

    Object findByNamePlayer(String name);

    List<String> findNamePlayerByDataPlayerWinOrderByDataPlayerWinDesc();
}

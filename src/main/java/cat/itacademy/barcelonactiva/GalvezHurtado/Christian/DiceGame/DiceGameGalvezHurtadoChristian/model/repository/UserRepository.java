package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    boolean existsById(String idPlayer);

    Optional<UserEntity> findById(String idPlayer);

    Optional<UserEntity> findByName(String username);

    boolean existsByName(String nameUser);

    //List<String> findNamePlayerByDataPlayerResultOrderByDataPlayerResultDesc();
}

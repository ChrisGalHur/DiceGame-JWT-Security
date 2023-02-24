package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    List<PlayerDTO> getAllPlayersDTO();

    void savePlayer(PlayerEntity playerEntity);

    Optional<PlayerEntity> getPlayerByID(int id);

    void deletePlayerById(int id);

    boolean nameExists(String name);

    boolean existById(int idPlayer);

    boolean play(PlayerEntity playerEntity);

    /*Optional<List<DataPlayerEntity>> findWinner(String idPlayer);*/

    /*String getLoser();*/

    Optional<PlayerEntity> findWinner();
}

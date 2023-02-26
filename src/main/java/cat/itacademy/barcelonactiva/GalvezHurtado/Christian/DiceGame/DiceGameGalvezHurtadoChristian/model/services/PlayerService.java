package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    //Encontrar
    Optional<List<Object>> findHistoryPlayer(int idPlayer);

    void savePlayer(PlayerDTO playerDTO);

    PlayerDTO getPlayerByID(int id);

    boolean existById(int idPlayer);

    boolean nameExists(String name);

    Optional<List<Object>> findWinner();

    Optional<List<Object>> findLoser();

    Optional<List<Object>> findAllWithPercentage();

    //Guardar y modificar
    boolean play(PlayerEntity playerEntity);

    //Eliminar
    void deleteHistoryPlayer(int idPlayer);

    Optional<Object> getTotalAverage();
}

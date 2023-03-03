package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface PlayerService {

    //Encontrar
    /*Optional<List<Object>> findHistoryPlayer(int idPlayer);*/

    void savePlayer(PlayerDTO playerDTO);

    PlayerDTO getPlayerByIdPlayer(String id);

    boolean nameExists(String name);

    Object findWinner();

    /*Object findLoser();*/

    List<PlayerDTO> findAllWithPercentage();

    boolean existByIdPlayer(String idPlayer);

    //Guardar y modificar
    boolean play(PlayerDTO playerEntity);

    //Eliminar
    /*void deleteHistoryPlayer(int idPlayer);*/

    /*Optional<Object> getTotalAverage();*/
}

package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.DataPlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {


    List<DataPlayerDTO> findHistoryPlayer(String idPlayer);

    void updatePlayer(PlayerDTO playerDTO);

    PlayerDTO getPlayerByIdPlayer(String id);

    boolean nameExists(String name);

    Object findLoser();

    Object findWinner();

    List<PlayerDTO> findAllWithPercentage();

    boolean existByIdPlayer(String idPlayer);

    UserEntity findByIdPlayer(String idPlayer);

    void deleteHistoryPlayer(String idPlayer);

    String play(String id);

    /*void deleteHistoryPlayer(int idPlayer);*/

    double getTotalAverage();
}

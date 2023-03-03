package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final DataPlayerServiceImpl dataPlayerServiceImpl;

    public void deleteAll() {
        playerRepository.deleteAll();
    }
    @Override
    public List<PlayerDTO> findAllWithPercentage() {
        List<PlayerDTO> playersDTO = playerRepository.findAll().stream()
                .map(x -> {
                    return modelMapper.map(x, PlayerDTO.class);
                }).collect(Collectors.toList());

        return playersDTO;
    }

    @Override
    public void savePlayer(PlayerDTO playerDTO) {
        this.playerRepository.save(modelMapper.map(playerDTO, PlayerEntity.class));
    }

    @Override
    public PlayerDTO getPlayerByIdPlayer(String id) {
        return modelMapper.map(playerRepository.findByIdPlayer(id), PlayerDTO.class);
    }

    @Override
    public boolean nameExists(String name) {
        try{
            return playerRepository.findAll().stream().anyMatch(x -> x.getNamePlayer().equals(name));
        }catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    @Override
    public boolean existByIdPlayer(String idPlayer) {
        return playerRepository.existsByIdPlayer(idPlayer);
    }

    /*@Override
    public void deleteHistoryPlayer(int idPlayer) {
        playerRepository.deleteHistoryPlayer(idPlayer);
    }*/

    @Override
    public boolean play(PlayerDTO playerDTO) {

        PlayerEntity playerEntity = modelMapper.map(playerDTO, PlayerEntity.class);
        int[] game = Game.roll();
        boolean resultPlay = game[0] + game[1] == 7;

        DataPlayerEntity saving = new DataPlayerEntity(game[0], game[1],resultPlay, playerEntity);
        playerEntity.getDataPlayer().add(saving);
        dataPlayerServiceImpl.saveDataPlayer(saving);
        return resultPlay;
    }

    @Override
    public List<String> findWinner(){
            return playerRepository.findNamePlayerByDataPlayerWinOrderByDataPlayerWinDesc();
    }

    /*@Override
    public Object findLoser() {
        return playerRepository.findNameByDataPlayerEntityFindByTopByOrderByWinDesc();
    }*/

    /*@Override
    public Optional<List<Object>> findHistoryPlayer(int idPlayer) {
        return playerRepository.findHistoryPlayer(idPlayer);
    }*/

    /*@Override
    public Optional<Object> getTotalAverage() {
        return playerRepository.getTotalAverage();
    }*/
}

package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final DataPlayerServiceImpl dataPlayerServiceImpl;

    @Override
    public Optional<List<Object>> findAllWithPercentage() {
        return playerRepository.findAllWithPercentage();
    }

    @Override
    public void savePlayer(PlayerDTO playerDTO) {
        this.playerRepository.save(modelMapper.map(playerDTO, PlayerEntity.class));
    }

    @Override
    public PlayerDTO getPlayerByID(int id) {
        return modelMapper.map(playerRepository.findById(id), PlayerDTO.class);
    }

    @Override
    public boolean nameExists(String name) {
        return playerRepository.findAll().stream().anyMatch(x -> x.getName().equals(name));
    }

    @Override
    public boolean existById(int idPlayer) {
        return playerRepository.existsById(idPlayer);
    }

    @Override
    public void deleteHistoryPlayer(int idPlayer) {
        playerRepository.deleteHistoryPlayer(idPlayer);
    }

    @Override
    public boolean play(PlayerEntity playerEntity) {
        int[] game = Game.roll();
        boolean resultPlay = game[0] + game[1] == 7;

        DataPlayerEntity saving = new DataPlayerEntity(game[0], game[1],resultPlay, playerEntity);
        playerEntity.getDataPlayer().add(saving);
        dataPlayerServiceImpl.saveDataPlayer(saving);
        return resultPlay;
    }

    @Override
    public Optional<List<Object>> findWinner(){
            return playerRepository.findByWinner();
    }

    @Override
    public Optional<List<Object>> findLoser() {
        return playerRepository.findByLoser();
    }

    @Override
    public Optional<List<Object>> findHistoryPlayer(int idPlayer) {
        return playerRepository.findHistoryPlayer(idPlayer);
    }

    @Override
    public Optional<Object> getTotalAverage() {
        return playerRepository.getTotalAverage();
    }
}

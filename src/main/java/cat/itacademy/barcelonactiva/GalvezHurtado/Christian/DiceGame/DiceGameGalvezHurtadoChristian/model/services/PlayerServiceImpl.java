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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final DataPlayerServiceImpl dataPlayerServiceImpl;

    @Override
    public List<PlayerDTO> getAllPlayersDTO() {
        return playerRepository.findAll().stream()
                .map(x -> {
                    return modelMapper.map(x, PlayerDTO.class);
                }).collect(Collectors.toList());
    }

    @Override
    public void savePlayer(PlayerEntity playerEntity) {
        this.playerRepository.save(playerEntity);
    }

    @Override
    public Optional<PlayerEntity> getPlayerByID(int id) {
        return playerRepository.findById(id);
    }

    @Override
    public void deletePlayerById(int id) {

    }

    @Override
    public boolean nameExists(String name) {
        return playerRepository.findAll().stream().anyMatch(x -> x.getNamePlayer().equals(name));
    }

    @Override
    public boolean existById(int idPlayer) {
        return playerRepository.existsById(idPlayer);
    }

    /*@Override
    public void deletePlayerHistory(int idPlayer) {
        dataPlayerServiceImpl.   ;
    }*/

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
    public Optional<PlayerEntity> findWinner(){
        return playerRepository.findWinner();
    }
    /*@Override
    public String getLoser() {
        return playerRepository.getLoser();
    }

    @Override
    public String getWinner() {
        return playerRepository.getWinner();
    }*/
}

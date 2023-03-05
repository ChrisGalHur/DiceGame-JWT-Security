package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.DataPlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public void deleteAll() {
        playerRepository.deleteAll();
    }

    @Override
    public List<PlayerDTO> findAllWithPercentage() {
        //Entity list
        List<PlayerEntity> players = playerRepository.findAll();

        //Sending list and returns list DTOs only  with name and percentage
        List<PlayerDTO> playersWithPercentage = players.stream()
                .map(y -> getPlayerAndPercentage(y)).toList();

        return playersWithPercentage;
    }

    @Override
    public void savePlayer(PlayerDTO playerDTO) {
        PlayerEntity player = playerRepository.findById(playerDTO.getId()).get();
        player.setName(playerDTO.getName());
        this.playerRepository.save(player);
    }

    @Override
    public PlayerDTO getPlayerByIdPlayer(String id) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(id);
        PlayerDTO playerDTO =  modelMapper.map(playerEntity.get(), PlayerDTO.class);
        return playerDTO;
    }

    @Override
    public boolean nameExists(String name) {
            return playerRepository.findAll().stream().anyMatch(x -> x.getName().equals(name));
    }

    @Override
    public boolean existByIdPlayer(String idPlayer) {
        return playerRepository.existsById(idPlayer);
    }

    @Override
    public PlayerEntity findByIdPlayer(String idPlayer) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(idPlayer);
        return playerEntity.get();
    }

    @Override
    public void deleteHistoryPlayer(String idPlayer){
        PlayerEntity playerDeletingHistory = playerRepository.findById(idPlayer).get();
        playerDeletingHistory.getDataPlayer().clear();
        playerRepository.save(playerDeletingHistory);
    }

    @Override
    public String play(String id) {
        PlayerEntity playerGaming = playerRepository.findById(id).get();

        DataPlayerEntity saving = Game.roll();

        if(playerGaming.getDataPlayer() == null){
            playerGaming.setDataPlayer(new ArrayList<>());
        }

        playerGaming.getDataPlayer().add(saving);
        playerRepository.save(playerGaming);
        return saving.getResult();
    }

    @Override
    public List<DataPlayerDTO> findHistoryPlayer(String idPlayer) {
        List<DataPlayerDTO> historyPlayerDTO = findByIdPlayer(idPlayer).getDataPlayer().stream()
                .map(x -> modelMapper.map(x, DataPlayerDTO.class))
                .toList();

        return historyPlayerDTO;
    }

    @Override
    public double getTotalAverage() {
        double totalAverage;
        List<PlayerDTO> playersAndPercentage = findAllWithPercentage();
        totalAverage = playersAndPercentage.stream()
                .mapToDouble(PlayerDTO::getPercentage)
                .average()
                .orElse(Double.NaN);

        return totalAverage;
    }

    @Override
    public PlayerDTO findLoser() {
        List<PlayerDTO> playersAndPercentage = findAllWithPercentage();
        PlayerDTO loser = playersAndPercentage.stream()
                .min(Comparator.comparingDouble(PlayerDTO::getPercentage))
                .orElse(null);

        return loser;
    }

    @Override
    public PlayerDTO findWinner(){
        List<PlayerDTO> playersAndPercentage = findAllWithPercentage();
        PlayerDTO winner = playersAndPercentage.stream()
                .max(Comparator.comparingDouble(PlayerDTO::getPercentage))
                .orElse(null);

        return winner;
    }


    //<EXTRAS>-----------------------------------------------------------
    public PlayerDTO getPlayerAndPercentage(PlayerEntity playerEntity) {
        int wins = 0;
        double percent;

        //Separate with size list 0 and > 0 to save name
        if(playerEntity.getDataPlayer().size() > 0) {
            for (int i = 0; i < playerEntity.getDataPlayer().size(); i++) {
                if (playerEntity.getDataPlayer().get(i).getResult().equals("WINNER")) {
                    wins++;
                }
            }
            percent = (((double)wins * 100) / (double) playerEntity.getDataPlayer().size());
            return new PlayerDTO(playerEntity.getName(), percent);
        }else{
            return new PlayerDTO(playerEntity.getName(), 0);
        }
    }
    //</EXTRAS>-----------------------------------------------------------

}

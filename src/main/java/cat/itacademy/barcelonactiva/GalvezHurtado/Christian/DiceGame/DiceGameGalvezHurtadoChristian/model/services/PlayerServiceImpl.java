package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.Role;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.DataPlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.RoleRepository;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public void saveNewUser(PlayerDTO playerDTO) {
        UserEntity userEntity = modelMapper.map(playerDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(playerDTO.getPassword()));

        Role roleUser = roleService.findByName("USER").get();
        userEntity.setRole(Collections.singletonList(roleUser));
        this.userRepository.save(userEntity);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<PlayerDTO> findAllWithPercentage() {
        //Entity list
        List<UserEntity> players = userRepository.findAll();

        //Sending list and returns list DTOs only  with name and percentage
        List<PlayerDTO> playersWithPercentage = players.stream()
                .map(y -> getPlayerAndPercentage(y)).toList();

        return playersWithPercentage;
    }

    @Override
    public void updatePlayer(PlayerDTO playerDTO) {
        UserEntity player = userRepository.findById(playerDTO.getId()).get();
        player.setName(playerDTO.getName());
        this.userRepository.save(player);
    }

    @Override
    public PlayerDTO getPlayerByIdPlayer(String id) {
        Optional<UserEntity> playerEntity = userRepository.findById(id);
        PlayerDTO playerDTO =  modelMapper.map(playerEntity.get(), PlayerDTO.class);
        return playerDTO;
    }

    @Override
    public boolean nameExists(String name) {
            return userRepository.findAll().stream().anyMatch(x -> x.getName().equals(name));
    }

    @Override
    public boolean existByIdPlayer(String idPlayer) {
        return userRepository.existsById(idPlayer);
    }

    @Override
    public UserEntity findByIdPlayer(String idPlayer) {
        Optional<UserEntity> playerEntity = userRepository.findById(idPlayer);
        return playerEntity.get();
    }

    @Override
    public void deleteHistoryPlayer(String idPlayer){
        UserEntity playerDeletingHistory = userRepository.findById(idPlayer).get();
        playerDeletingHistory.getDataPlayer().clear();
        userRepository.save(playerDeletingHistory);
    }

    @Override
    public String play(String id) {
        UserEntity playerGaming = userRepository.findById(id).get();

        DataPlayerEntity saving = Game.roll();

        if(playerGaming.getDataPlayer() == null){
            playerGaming.setDataPlayer(new ArrayList<>());
        }

        playerGaming.getDataPlayer().add(saving);
        userRepository.save(playerGaming);
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


    //
    public PlayerDTO getPlayerAndPercentage(UserEntity userEntity) {
        int wins = 0;
        double percent;

        //Separate with size list 0 and > 0 to save name
        if(userEntity.getDataPlayer().size() > 0) {
            for (int i = 0; i < userEntity.getDataPlayer().size(); i++) {
                if (userEntity.getDataPlayer().get(i).getResult().equals("WINNER")) {
                    wins++;
                }
            }
            percent = (((double)wins * 100) / (double) userEntity.getDataPlayer().size());
            return new PlayerDTO(userEntity.getName(), percent);
        }else{
            return new PlayerDTO(userEntity.getName(), 0);
        }
    }

    public UserEntity findByName(String name) {
        return userRepository.findByName(name).get();
    }
    //</EXTRAS>-----------------------------------------------------------
}

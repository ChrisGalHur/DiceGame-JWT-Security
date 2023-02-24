package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.controllers;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.Message;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services.PlayerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final ModelMapper modelMapper;
    private final PlayerServiceImpl playerServiceImpl;

    @PostMapping("/add")//Añadir jugador
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO playerDTO){
        PlayerEntity playerEntity = modelMapper.map(playerDTO, PlayerEntity.class);//Transformo de DTO a Entity
        boolean found = playerServiceImpl.nameExists(playerEntity.getNamePlayer());

        if(playerDTO.getNamePlayer() == null){
            playerEntity.setNamePlayer("UNKNOWN");
            playerServiceImpl.savePlayer(playerEntity);
            PlayerDTO playerToShow = modelMapper.map(playerEntity, PlayerDTO.class);
            return new ResponseEntity<>(new Message("Your name is: " + playerToShow.getNamePlayer()),HttpStatus.OK);
        }else if (found){
            return new ResponseEntity<>(new Message("This name already exists."), HttpStatus.BAD_REQUEST);
        }else{
            playerServiceImpl.savePlayer(playerEntity);
            return new ResponseEntity<>(new Message("Player added."), HttpStatus.OK);
        }
    }

    @PutMapping("/update")//Cambiar nombre Jugador
    public ResponseEntity<?> updatePlayer(@RequestBody PlayerDTO playerDTO){

        if (!playerServiceImpl.existById(playerDTO.getId())){
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else if (playerDTO.getNamePlayer() == null){
            return new ResponseEntity<>(new Message("Name is required."), HttpStatus.BAD_REQUEST);
        }else {
            PlayerEntity playerEntity = modelMapper.map(playerDTO, PlayerEntity.class);
            playerServiceImpl.savePlayer(playerEntity);
            return new ResponseEntity<>(new Message("Name of player has been changed to " + playerDTO.getNamePlayer() + "."), HttpStatus.OK);
        }
    }

    @GetMapping("/getOne/{id}")//Ver un jugador
    public ResponseEntity<PlayerDTO> getOnePlayer(@PathVariable("id") int idPlayer){

        if (!playerServiceImpl.existById(idPlayer)) {
            return new ResponseEntity(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else {
            PlayerDTO playerDTO = modelMapper.map(playerServiceImpl.getPlayerByID(idPlayer).get(), PlayerDTO.class);
            return new ResponseEntity<>(playerDTO, HttpStatus.OK);
        }
    }

    /*@DeleteMapping("/{id}/delHistory")//Eliminar historial jugador
    public ResponseEntity<?> deleteAllPlayers(@PathVariable("id") int idPlayer){
        d;
        //playerServiceImpl.deletePlayerHistory(idPlayer);
        return new ResponseEntity<>(new Message("History has been deleted."), HttpStatus.OK);
    }*/

    @GetMapping("/getAll")//Ver todos
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        List<PlayerDTO> players = playerServiceImpl.getAllPlayersDTO();
        //Falta porcentaje
        return new ResponseEntity<List<PlayerDTO>>(players, HttpStatus.OK);
    }

    @PostMapping("/play/{id}")//jugar partida
    public ResponseEntity<?> playGame(@PathVariable("id") int idPlayer){
        PlayerEntity playerPlaying = playerServiceImpl.getPlayerByID(idPlayer).get();

        if(playerServiceImpl.play(playerPlaying)){
            return new ResponseEntity<>(new Message("You win!!!"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Message("You Lose!!!"), HttpStatus.OK);
        }
    }

    /*@GetMapping("/getGamesPlayer/{Id}")
    public ResponseEntity<List<DataPlayerEntity>> findByGamesPlayer(@PathVariable("idPlayer") int idPlayer){
        Optional<List<DataPlayerEntity>> gamesPlayer = playerServiceImpl.findWinner(idPlayer);
        return new ResponseEntity<>(new Message("ids:" + i   ), HttpStatus.OK);
    }*/

    /*
    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getLoser(){
        String nameLoser = playerServiceImpl.getLoser();

        return new ResponseEntity<>(new Message(nameLoser), HttpStatus.OK);
    }*/

    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getWinner(){
        Optional<PlayerEntity>  player = playerServiceImpl.findWinner();

        return new ResponseEntity<Optional<PlayerEntity>>(player, HttpStatus.OK);
    }
}

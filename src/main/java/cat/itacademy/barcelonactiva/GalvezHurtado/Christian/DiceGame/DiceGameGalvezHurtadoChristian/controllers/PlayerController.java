package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.controllers;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.Message;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services.PlayerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final ModelMapper modelMapper;
    private final PlayerServiceImpl playerServiceImpl;

    @DeleteMapping("/deleteAll")//Eliminar historial jugador ENTY/DTO V
    public ResponseEntity<?> deleteAll() {
        playerServiceImpl.deleteAll();

        return new ResponseEntity<>(new Message("All deleted"), HttpStatus.OK);
    }

    @PostMapping("/add")//Añadir jugador ENTY/DTO V
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO playerDTO){
        if(playerDTO == null){
            playerDTO.setNamePlayer("UNKNOWN");
            playerServiceImpl.savePlayer(playerDTO);
            return new ResponseEntity<>(new Message("Your name is: " + playerDTO.getNamePlayer()),HttpStatus.OK);
        }else if (playerServiceImpl.nameExists(playerDTO.getNamePlayer())){
            return new ResponseEntity<>(new Message("This name already exists."), HttpStatus.BAD_REQUEST);
        }else{
            playerServiceImpl.savePlayer(playerDTO);
            return new ResponseEntity<>(new Message("Player added."), HttpStatus.OK);
        }
    }

    @PutMapping("/update/{id}")//Cambiar nombre Jugador ENTY/DTO V
    public ResponseEntity<?> updatePlayer(@PathVariable("id") String id,@RequestBody PlayerDTO playerDTO){

        if (!playerServiceImpl.existByIdPlayer(id)){
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else if (playerDTO.getNamePlayer() == null){
            return new ResponseEntity<>(new Message("Name is required."), HttpStatus.BAD_REQUEST);
        }else if (playerDTO.getIdPlayer() == 0){
            return new ResponseEntity<>(new Message("Id is required."), HttpStatus.BAD_REQUEST);
        }else {
            if(playerServiceImpl.nameExists(playerDTO.getNamePlayer())){
                return new ResponseEntity<>(new Message("This name already exists."), HttpStatus.BAD_REQUEST);
            }else {
                playerServiceImpl.savePlayer(playerDTO);
                return new ResponseEntity<>(new Message("Name of player has been changed to " + playerDTO.getNamePlayer() + "."), HttpStatus.OK);
            }
        }
    }

    @GetMapping("/getOne/{id}")//Ver un jugador ENTY/DTO V
    public ResponseEntity<?> getOnePlayer(@PathVariable("id") String id){

        if (!playerServiceImpl.existByIdPlayer(id)) {
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else {
            PlayerDTO playerDTO = playerServiceImpl.getPlayerByIdPlayer(id);
            return new ResponseEntity<>(playerDTO, HttpStatus.OK);
        }
    }

    @PostMapping("/play/{id}")//jugar partida ENTY/DTO enService
    public ResponseEntity<?> playGame(@PathVariable("id") String id){

        if(!playerServiceImpl.existByIdPlayer(id)) {
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else {
            PlayerDTO playerPlaying = playerServiceImpl.getPlayerByIdPlayer(id);
            if (playerServiceImpl.play(playerPlaying)) {
                return new ResponseEntity<>(new Message("You win!!!"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Message("You Lose!!!"), HttpStatus.OK);
            }
        }
    }


    @GetMapping("/ranking/winner") //ENTY/DTO ?
    public ResponseEntity <List<String>> getWinner(){
        List playerWinner = playerServiceImpl.findWinner();

        return new ResponseEntity<List<String>>(playerWinner, HttpStatus.OK);
    }

    /*@GetMapping("/ranking/loser") //ENTY/DTO ?
    public ResponseEntity<Optional<List<Object>>> getLoser(){
        Optional<List<Object>> playerLoser = playerServiceImpl.findLoser();

        return new ResponseEntity<Optional<List<Object>>>(playerLoser, HttpStatus.OK);
    }*/

    /*@DeleteMapping("/{id}/delHistory")//Eliminar historial jugador ENTY/DTO V
    public ResponseEntity<?> deleteHistoryPlayer(@PathVariable("id") int idPlayer){
        playerServiceImpl.deleteHistoryPlayer(idPlayer);

        return new ResponseEntity<>(new Message("History has been deleted."), HttpStatus.OK);
    }*/

    /*@GetMapping("/getHistoryPlayer/{id}") //ENTY/DTO ?
    public ResponseEntity<Optional<List<Object>>> findByGamesPlayer(@PathVariable("id") int idPlayer){
        Optional<List<Object>> historyPlayer = playerServiceImpl.findHistoryPlayer(idPlayer);

        return new ResponseEntity<Optional<List<Object>>>(historyPlayer, HttpStatus.OK);
    }*/

    /*@GetMapping("/getAll")//Ver todos ENTY/DTO V
    public ResponseEntity<?> getAllPlayers(){
        List<PlayerDTO> players = playerServiceImpl.findAllWithPercentage();

        if(players.isEmpty()){
            return new ResponseEntity<>(new Message("There are no players."), HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<List<PlayerDTO>>(players, HttpStatus.OK);
        }
    }*/

    /*@GetMapping("/ranking/totalAverage") //ENTY/DTO ?
    public ResponseEntity<Optional<Object>> getTotalAverage(){
        Optional<Object> totalAverage = playerServiceImpl.getTotalAverage();

        return new ResponseEntity<Optional<Object>>(totalAverage, HttpStatus.OK);
    }*/
}

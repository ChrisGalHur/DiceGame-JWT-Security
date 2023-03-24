package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.controllers;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.DataPlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.Message;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.security.CustomAuthenticationManager;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services.JWTGenerator;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services.PlayerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerServiceImpl playerServiceImpl;
    private final CustomAuthenticationManager customAuthenticationManager;

    private final JWTGenerator jwtGenerator;

    //region POST
    @PostMapping("/play/{id}")//Play Game ENTY/DTO V
    public ResponseEntity<?> playGame(@PathVariable("id") String id, HttpServletRequest request){
        if(!playerServiceImpl.existByIdPlayer(id)) {
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else {
            Message message = new Message(playerServiceImpl.play(id));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
    //endregion POST

    //region PUT
    @PutMapping("/update")//Cambiar nombre Jugador ENTY/DTO V
    public ResponseEntity<?> updatePlayer(@RequestBody PlayerDTO playerDTO){

        if (!playerServiceImpl.existByIdPlayer(playerDTO.getId())){
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else if (playerDTO.getName() == null){
            return new ResponseEntity<>(new Message("Name is required."), HttpStatus.BAD_REQUEST);
        }else if (playerDTO.getId() == null){
            return new ResponseEntity<>(new Message("Id is required."), HttpStatus.BAD_REQUEST);
        }else {
            if(playerServiceImpl.nameExists(playerDTO.getName())){
                return new ResponseEntity<>(new Message("This name already exists."), HttpStatus.BAD_REQUEST);
            }else {
                playerServiceImpl.updatePlayer(playerDTO);
                return new ResponseEntity<>(new Message("Name of player has been changed to " + playerDTO.getName() + "."), HttpStatus.OK);
            }
        }
    }
    //endregion PUT

    //region DELETE
    @DeleteMapping("/deleteAll")//Eliminar historial jugador ENTY/DTO V
    public ResponseEntity<?> deleteAll() {
        playerServiceImpl.deleteAll();

        return new ResponseEntity<>(new Message("All deleted"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delHistory")//Eliminar historial jugador ENTY/DTO V
    public ResponseEntity<?> deleteHistoryPlayer(@PathVariable("id") String idPlayer){

        if(!playerServiceImpl.existByIdPlayer(idPlayer)) {
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);

        }else {
            playerServiceImpl.deleteHistoryPlayer(idPlayer);
            return new ResponseEntity<>(new Message("History has been deleted."), HttpStatus.OK);
        }
    }
    //endregion DELETE

    //region GET
    @GetMapping("/getOne/{id}")//Ver un jugador ENTY/DTO V
    public ResponseEntity<?> getOnePlayer(@PathVariable("id") String id){

        if (!playerServiceImpl.existByIdPlayer(id)) {
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else {
            PlayerDTO playerDTO = playerServiceImpl.getPlayerByIdPlayer(id);
            return new ResponseEntity<>(playerDTO, HttpStatus.OK);
        }
    }

    @GetMapping("/getHistoryPlayer/{id}") //ENTY/DTO ?
    public ResponseEntity<?> findByGamesPlayer(@PathVariable("id") String idPlayer){
        if (!playerServiceImpl.existByIdPlayer(idPlayer)) {
            return new ResponseEntity<>(new Message("This Player doesn't exist."), HttpStatus.NOT_FOUND);
        }else{
            List<DataPlayerDTO> historyPlayer = playerServiceImpl.findHistoryPlayer(idPlayer);
            return ResponseEntity.ok(historyPlayer);
        }
    }

    @GetMapping("/getAll")//Ver todos ENTY/DTO V
    public ResponseEntity<?> getAllPlayers(){
        List<PlayerDTO> players = playerServiceImpl.findAllWithPercentage();

        if(players.isEmpty()){
            return new ResponseEntity<>(new Message("There are no players."), HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(players);
        }
    }

    @GetMapping("/ranking/totalAverage") //ENTY/DTO ?
    public ResponseEntity<Object> getTotalAverage(){
        double totalAverage = playerServiceImpl.getTotalAverage();
        
        if(!Double.isNaN(totalAverage)){
            return new ResponseEntity<>(totalAverage, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Message("There are no players."), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ranking/winner") //ENTY/DTO ?
    public ResponseEntity<Object> getWinner(){
        PlayerDTO winner = playerServiceImpl.findWinner();
        if (!(winner == null)){
            return new ResponseEntity<>(winner, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Message("Don't have winner"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ranking/loser") //ENTY/DTO ?
    public ResponseEntity<Object> getLoser(){
        PlayerDTO loser = playerServiceImpl.findLoser();
        if (!(loser == null)){
            return new ResponseEntity<>(loser, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Message("Don't have loser"), HttpStatus.NOT_FOUND);
        }
    }
    //endregion GET
}
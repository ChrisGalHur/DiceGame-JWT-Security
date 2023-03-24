package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.controllers;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.Role;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.AuthResponseDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.LoginDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.security.CustomAuthenticationManager;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services.JWTGenerator;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services.PlayerServiceImpl;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserRepository userRepository;
    private final PlayerServiceImpl playerServiceimpl;
    private final RoleService roleService;
    private final JWTGenerator jwtGenerator;


    //region LOGIN-REGISTER
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getName(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody PlayerDTO userDTO) {
        if (userRepository.existsByName((userDTO.getName()))) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        if (userDTO.getName() == null || userDTO.getName().equals("")) {
            userDTO.setName("UNKNOWN");
            playerServiceimpl.saveNewUser(userDTO);
            return new ResponseEntity<>("Your name is: " + userDTO.getName(), HttpStatus.OK);
        }

        playerServiceimpl.saveNewUser(userDTO);
        return new ResponseEntity<>("User registered!", HttpStatus.OK);
    }
    //endregion LOGIN-REGISTER

    //region ROLES
    @PostMapping("/add/{pass}")
    public ResponseEntity<String> addRole(@RequestBody Role role, @PathVariable("pass") String pass) {
        final String PASSWORD = "SoloYo";

        if (!pass.equals(PASSWORD)) {
            return new ResponseEntity<>("Password wrong", HttpStatus.BAD_REQUEST);
        } else if (roleService.existsByName(role.getName())) {
            return new ResponseEntity<>("Role already exists", HttpStatus.BAD_REQUEST);
        } else if (pass.equals(PASSWORD)) {
            roleService.saveRole(role);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failure", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{pass}")
    public ResponseEntity<String> deleteRole(@RequestBody Role role, @PathVariable("pass") String pass) {
        final String PASSWORD = "SoloYo";
        if (pass.equals(PASSWORD) && !roleService.existsByName(PASSWORD)) {
            roleService.deleteRole(role);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("failure", HttpStatus.BAD_REQUEST);
    }
    //endregion ROLES
}
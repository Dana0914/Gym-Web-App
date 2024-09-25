package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.TrainerRequestDTO;
import epam.com.gymapplication.dto.TrainerResponseDTO;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(value = "/register" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<TrainerResponseDTO> registerTrainer(@RequestBody
                                                              TrainerRequestDTO trainerRequestDTO) {
        TrainerResponseDTO registeredTrainerRequest = trainerService.createTrainerProfile(trainerRequestDTO);


        return new ResponseEntity<>(registeredTrainerRequest, HttpStatus.CREATED);

    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {

        boolean authenticatedTrainerProfile = trainerService.authenticateTrainerProfile(username, password);
        if (authenticatedTrainerProfile) {
            return ResponseEntity.ok("Authorized successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PutMapping("/change-login")
    public ResponseEntity<String> changeLogin(@RequestParam("username") String username,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldPassword") String oldPassword) {

        boolean passwordChange = trainerService.changePassword(username, password, oldPassword);
        if (passwordChange) {
            return ResponseEntity.ok("Password changed successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }




}

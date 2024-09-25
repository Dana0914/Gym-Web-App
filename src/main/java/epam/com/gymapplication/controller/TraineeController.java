package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.TraineeProfileResponseDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.model.TraineeRequest;
import epam.com.gymapplication.model.TraineeResponse;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import epam.com.gymapplication.service.TraineeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/trainees")
public class TraineeController {
    private final TraineeService traineeService;
    private final ModelMapper modelMapper;
    private final UserProfileService userProfileService;
    private final PasswordGenerator passwordGenerator;

    public TraineeController(TraineeService traineeService,
                             ModelMapper modelMapper,
                             UserProfileService userProfileService,
                             PasswordGenerator passwordGenerator) {
        this.traineeService = traineeService;
        this.modelMapper = modelMapper;
        this.userProfileService = userProfileService;
        this.passwordGenerator = passwordGenerator;
    }



    @PostMapping(value = "/register" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<TraineeResponse> registerTrainee(@RequestBody
                                                           TraineeRequest traineeRequest) {

        TraineeResponse registeredTraineeRequest = traineeService.createTraineeProfile(traineeRequest);


        return new ResponseEntity<>(registeredTraineeRequest, HttpStatus.CREATED);

    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username,
                                                 @RequestParam("password") String password) {

        boolean authenticatedTraineeProfile = traineeService.authenticateTraineeProfile(username, password);
        if (authenticatedTraineeProfile) {
            return ResponseEntity.ok("Authorized successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PutMapping("/change-login")
    public ResponseEntity<String> changeLogin(@RequestParam("username") String username,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldPassword") String oldPassword) {

        boolean passwordChange = traineeService.changePassword(username, password, oldPassword);
        if (passwordChange) {
            return ResponseEntity.ok("Password changed successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @GetMapping(value = "/trainee-profile")
    public TraineeProfileResponseDTO getTraineeProfile(@RequestParam("username") String username) {
        TraineeProfileResponseDTO traineeProfileByUsername = traineeService.findTraineeProfileByUsername(username);

        return traineeProfileByUsername;

    }
















}

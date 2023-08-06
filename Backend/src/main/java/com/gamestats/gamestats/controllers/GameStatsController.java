package com.gamestats.gamestats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamestats.gamestats.entities.Image;
import com.gamestats.gamestats.entities.User;
import com.gamestats.gamestats.entities.Valorant;
import com.gamestats.gamestats.models.GetPlayerInfo;
import com.gamestats.gamestats.responsehandler.ResponseHandler;
import com.gamestats.gamestats.services.EmailService;
import com.gamestats.gamestats.services.ImageService;
import com.gamestats.gamestats.services.UserService;
import com.gamestats.gamestats.services.ValorantService;
import com.gamestats.gamestats.util.ImageUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.InvalidParameterException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("api/v1/game-stats/users")
public class GameStatsController {

    @Autowired
    UserService userservice;
    @Autowired
    GetPlayerInfo loginplayer;
    @Autowired
    ValorantService valorantservice;
    @Autowired
    ImageService imageService;
    @Autowired
    EmailService emailService;

    @GetMapping("/sign-in-issue/get-by-email")
    public ResponseEntity<Object> sendEmail(final @RequestParam String email) {
        try {
            User user = userservice.getByEmail(email);

            if (user == null)
                throw new Exception();

            emailService.sendEmail(email, "User private data request", String.format("Username: %s\nTagline: %s\nPassword: %s", user.getUsername(), user.getTagline(), user.getPassword()));

            return ResponseHandler.generateResponse(HttpStatus.FOUND, "User data found and mail sent successfully", null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, "No user data with this email", null);
        }
    }

    @GetMapping("/image/{nameString}")
    public ResponseEntity<?> getImage(@PathVariable String nameString) {
        byte[] image = imageService.get(nameString);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    @Operation(summary = "Registers the users in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "User is invalid")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> register(final @RequestBody User user) throws Exception {
        try {
            User loginUser = userservice.loginUser(user);
            if (loginUser != null)
                throw new IllegalAccessException();

            loginplayer.setUsername(user.getUsername());
            loginplayer.setTagline(user.getTagline());
            loginplayer.getInfo();

            if (user.getImage() == null)
                user.setImage(new Image());

            Boolean isPlayer = valorantservice.saveLoginPlayer(loginplayer, user, user.getId());
            loginplayer.reset();

            if (isPlayer) {
                if (user.getImage() == null)
                    user.setImage(new Image());

                User newUser = userservice.saveUser(user);

                return ResponseHandler.generateResponse(HttpStatus.CREATED,
                        "Successfully registerd the user in the database",
                        newUser);
            } else
                throw new Exception();

        } catch (IllegalAccessException e) {
            return ResponseHandler.generateResponse(HttpStatus.CONFLICT,
                    "User already exits, want to login?", null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.MULTI_STATUS,
                    "registeration Failed, Please try again later", null);
        }

    }

    @Operation(summary = "Displays the logged in user's info from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Login successfully"),
            @ApiResponse(responseCode = "400", description = "User is invalid")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> login(final @RequestBody User user) {
        try {
            // String names[] = playername.split("#", 0);
            User loginUser = userservice.loginUser(user);
            if (loginUser == null)
                throw new Exception();
            return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, "User successfully logged in", loginUser);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "User not found, Please provide all the neccessary details correctly", null);
        }
    }

    @Operation(summary = "Displays the player's stats by provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User is not registered"),
            @ApiResponse(responseCode = "400", description = "User is invalid")
    })
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(value = "/get-by-id", produces = "application/json")
    public ResponseEntity<Object> playerStats(final @RequestParam int id) {
        try {
            Valorant player = valorantservice.getPlayerById(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, "Player stats found", player);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "Player not found, Please provide a valid ID", null);
        }
    }

    @Operation(summary = "Displays the player's stats by provided name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User is not registered"),
            @ApiResponse(responseCode = "400", description = "User is invalid")
    })
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(value = "/get-by-name/{nameAndTag}", produces = "application/json")
    public ResponseEntity<Object> playerStats(final @PathVariable String nameAndTag) {
        try {
            String name[] = nameAndTag.split(" ");
            String username = name[0];
            String tagline = name[1];

            Valorant player = valorantservice.getPlayerByName(username, tagline);
            return ResponseHandler.generateResponse(HttpStatus.OK, "Player stats found", player);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "Player not found, Please provide a valid ID", null);
        }
    }

    @Operation(summary = "Displays all the player's stats from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success"),
            @ApiResponse(responseCode = "400", description = "User is invalid")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> allPlayerStats(final @RequestParam(required = false) String sort,
            final @RequestParam(required = true, defaultValue = "1") int page, final @RequestParam String like) {
        try {
            Iterable<Valorant> players = valorantservice.getAll(like);
            Iterable<User> users = userservice.getAll(like);

            JSONObject data = new JSONObject();

            data.put("users", users);
            data.put("players", players);

            return ResponseHandler.generateResponse(HttpStatus.OK, "Players found", data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }

    @Operation(summary = "Removes the user by ID from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success"),
            @ApiResponse(responseCode = "400", description = "ID is invalid")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/delete-by-id/{id}", produces = "application/json")
    public ResponseEntity<Object> delete(final @PathVariable int id, final @RequestParam String password) {
        try {
            Boolean isdeleted = userservice.deleteUser(id, password);
            if (!isdeleted)
                throw new InvalidParameterException();
            return ResponseHandler.generateResponse(HttpStatus.OK, "User with ID " + id + " is successfully deleted",
                    null);
        } catch (InvalidParameterException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "Password mismatch",
                    null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "User with ID " + id + " does not exists",
                    null);
        }
    }

    // @Operation(summary = "Removes the user by username and tagline from the
    // database")
    // @ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "Request Success"),
    // @ApiResponse(responseCode = "400", description = "username or tagline is
    // invalid")
    // })
    // @ResponseStatus(HttpStatus.OK)
    // @DeleteMapping(value = "/delete-by-name", produces = "application/json",
    // consumes = "application/json")
    // public ResponseEntity<Object> delete(final @RequestParam String username,
    // final @RequestParam String tagline) {
    // try {
    // userservice.deleteUser(username, tagline);
    // return ResponseHandler.generateResponse(HttpStatus.OK,
    // username + " " + tagline + " is successfully deleted",
    // null);
    // } catch (Exception e) {
    // return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
    // username + " " + tagline + " does not exists", null);
    // }
    // }

    @Operation(summary = "Updates the user info to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upadated Successfully"),
            @ApiResponse(responseCode = "400", description = "User is invalid")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/user/update", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> update(final @RequestPart("user") String userString,
            final @RequestPart("image") MultipartFile file) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userString, User.class);

            User updateUser = userservice.getUser(user.getId());

            Image image = new Image();

            if (!file.isEmpty() || file != null) {
                image.setId(user.getImage().getId());
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());
                image.setImage(ImageUtil.compressImage(file.getBytes()));
            }

            user.setImage(image);

            if (user.getEmail() != null)
                updateUser.setEmail(user.getEmail());
            if (user.getPhoneNo() != null)
                updateUser.setPhoneNo(user.getPhoneNo());
            if (user.getImage() != null)
                updateUser.setImage(user.getImage());

            userservice.updateUser(updateUser);
            return ResponseHandler.generateResponse(HttpStatus.OK, "Password updated successfully", updateUser);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_MODIFIED,
                    "Updatation Failed, Please try again later", null);
        }
    }
}

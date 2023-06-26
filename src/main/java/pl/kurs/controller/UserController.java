package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.model.dto.UserDto;
import pl.kurs.model.entity.User;
import pl.kurs.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        User user = service.saveNewUser(userDto);
        return ResponseEntity.status(201).body(new UserDto(user));
    }
}

package com.example.ignite_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    public final UserService userService;

    @PostMapping("/get")
    public ResponseEntity getUser() {
        log.info("User get");

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
    }

    @PostMapping("/insert")
    public ResponseEntity insertUser(@RequestBody UserModel userModel){
        log.info("User Insert");
        return ResponseEntity.status(HttpStatus.OK).body(userService.insertUser(userModel));
    }
    @PostMapping("/delete")
    public ResponseEntity deleteUser(@RequestBody UserModel userModel){
        log.info("User Delete");
        userService.deleteUser(userModel);
        return ResponseEntity.ok(null);
    }
}

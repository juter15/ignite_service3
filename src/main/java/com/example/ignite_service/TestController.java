package com.example.ignite_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final TestService testService;

    @PostMapping("/getTest")
    public ResponseEntity getTest(){

        return ResponseEntity.status(HttpStatus.OK).body(testService.getTest());
    }
}

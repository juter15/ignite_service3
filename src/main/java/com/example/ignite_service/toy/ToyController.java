package com.example.ignite_service.toy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Deprecated
@Controller
@RequiredArgsConstructor
@Slf4j
public class ToyController {
    /*
    *  Ignite > work > db > marshaller : 데이터를 읽는 Marshaller 디렉터리
    * */
    private final ToyService toyService;

    @PostMapping("/getToy")
    public ResponseEntity getToy(){
        log.info("get Toy");
        return ResponseEntity.status(HttpStatus.OK).body(toyService.getToy());
    }
}

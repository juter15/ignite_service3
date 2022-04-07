package com.example.ignite_service.toy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@Service
@RequiredArgsConstructor
@Slf4j
public class ToyService {
    private final ToyRepository toyRepository;

    public List<ToyModel> getToy(){
        Iterable<ToyModel> getToy = toyRepository.findAll();
        log.info("getToy: {}", getToy);

        List<ToyModel> toyList = new ArrayList<>();
        while (getToy.iterator().hasNext()){
            toyList.add(getToy.iterator().next());
        }


        return toyList;
    }
}

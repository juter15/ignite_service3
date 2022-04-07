package com.example.ignite_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final TestRepository testRepository;

    public List<TestModel> getTest(){
        Iterable<TestModel> getTest = testRepository.findAll();
        List<TestModel> testList = new ArrayList<>();

        while (getTest.iterator().hasNext()){
            testList.add(getTest.iterator().next());
        }
        return testList;
    }
}
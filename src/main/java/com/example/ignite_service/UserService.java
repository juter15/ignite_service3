package com.example.ignite_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserModel> getUser(){
        Iterable<UserModel> getUser =  userRepository.findAll();
        List<UserModel> userList = new ArrayList<>();
        while (getUser.iterator().hasNext()){
            userList.add(getUser.iterator().next());
        }
        return userList;
    }

    public List<UserModel> insertUser(UserModel userModel){
        Map<Long, UserModel> userModelMap = new TreeMap<>();
        userModelMap.put(userModel.getId(), userModel);

        List<UserModel> userList = new ArrayList<>();
        Iterable<UserModel> result = userRepository.save(userModelMap);
        for(UserModel user : result){
            userList.add(user);
        }
      return userList;
    }

    public void deleteUser(UserModel userModel){
        userRepository.deleteById(userModel.getId());
    }

    public void user(UserModel userModel){

    }
}

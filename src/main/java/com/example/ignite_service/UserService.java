package com.example.ignite_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.internal.processors.cache.CacheInvalidStateException;
import org.springframework.stereotype.Service;

import javax.cache.CacheException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Ignite ignite;
    public List<UserCache> getUser(){
        List<UserCache> userList = new ArrayList<>();
        Iterable<UserCache> getUser =  userRepository.findAll();
        while (getUser.iterator().hasNext()){
                userList.add(getUser.iterator().next());
            }
//        try{
//            Iterable<UserModel> getUser =  userRepository.findAll();
//            while (getUser.iterator().hasNext()){
//                userList.add(getUser.iterator().next());
//            }
//        }catch (CacheException e) {
//            if (e.getCause() instanceof CacheInvalidStateException) {
//                System.out.println(e.getCause().getMessage());
//                ignite.resetLostPartitions(Arrays.asList("User"));
//            }
//        }


        return userList;
    }

    public List<UserCache> insertUser(UserCache userModel){
        Map<Long, UserCache> userModelMap = new TreeMap<>();
        userModelMap.put(userModel.getId(), userModel);

        List<UserCache> userList = new ArrayList<>();
        try{
            Iterable<UserCache> result = userRepository.save(userModelMap);
            for(UserCache user : result){
                userList.add(user);
            }
        }catch (CacheException e) {
            if (e.getCause() instanceof CacheInvalidStateException) {
                System.out.println(e.getCause().getMessage());
            }
        }
      return userList;
    }

    public void deleteUser(UserCache userModel){
        userRepository.deleteById(userModel.getId());
    }

    public void user(UserCache userCache){

    }
}

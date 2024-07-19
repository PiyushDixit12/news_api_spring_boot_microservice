package com.userService.UserService.utils;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Blacklist {
    private Set<String> blackListTokenSet = new HashSet<>();

    public void blacKListToken(String token){
        blackListTokenSet.add(token);
    }
    public boolean isBlackListed(String token){
        return blackListTokenSet.contains(token);
    }
}


package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.entity.dto.UserDto;
import com.myprojects.splitbook.service.LoginService;
import com.myprojects.splitbook.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    @Autowired
    UserFriendService userFriendService;
    @Autowired
    LoginService loginService;

    @GetMapping("/getfriend")
    public List<UserDto> getFriendSuggestions(@RequestParam String query, Authentication authentication)
    {
        String key = query.trim().toLowerCase();
        if(key.equals(""))
        {
            return new ArrayList<>();
        }

        UserLogin user = loginService.getUserByUsername(authentication.getName());
        List<UserDto> users = userFriendService.findAllUsers();
        List<UserDto> res = users.stream()
                                 .filter(x->x.getUsername().toLowerCase().startsWith(key))
                                 .filter(x->x.getUid()!=user.getId())
                                 .collect(Collectors.toList());
        return res;

    }

    @GetMapping("/getfriendrequests")
    public List<UserDto> showFriendRequestList(Authentication authentication)
    {
        UserLogin user = loginService.getUserByUsername(authentication.getName());
        List<UserDto> res = userFriendService.getFriendRequests(user.getId());

        return res;
    }

    @GetMapping("/getmember")
    public List<UserDto> getMemberSuggestions(@RequestParam String query, Authentication authentication)
    {
        String key = query.trim().toLowerCase();
        if(key.equals(""))
        {
            return new ArrayList<>();
        }

        UserLogin user = loginService.getUserByUsername(authentication.getName());
        List<UserDto> friends = userFriendService.getAllFriends(user.getId());
        List<UserDto> res = friends.stream()
                                   .filter(x->x.getName().toLowerCase().startsWith(key))
                                   .collect(Collectors.toList());
        return res;
    }
}

package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.UserFriendsRepository;
import com.myprojects.splitbook.dao.UserLoginRepository;
import com.myprojects.splitbook.entity.EntityStatus;
import com.myprojects.splitbook.entity.UserFriendMapping;
import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFriendService {

    @Autowired
    UserFriendsRepository userFriendsRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    public String sendFriendRequest(int user1, int user2)
    {
        List<UserDto> friends = getAllFriends(user1);
        for(UserDto x : friends)
        {
            if(x.getUid()==user2)
            {
                return "Already your friend";
            }
        }

        UserFriendMapping newRequest = new UserFriendMapping();
        newRequest.setUser1(user1);
        newRequest.setUser2(user2);
        newRequest.setEntityStatus(EntityStatus.REQUESTED);

        boolean res = userFriendsRepository.insertFriendMapping(newRequest);
        if(!res)
        {
            return "Couldn't send request at this moment.";
        }
        return "Friend request sent!";
    }

    public String acceptFriendRequest(int user1, int user2)
    {
        UserFriendMapping entity = userFriendsRepository.findByKeys(user1,user2);
        if(entity==null)
        {
            return "Unexpected error. User mapping not found!";
        }
        entity.setEntityStatus(EntityStatus.ACCEPTED);
        boolean res1 = userFriendsRepository.insertFriendMapping(entity);

        UserFriendMapping reverseMapping = new UserFriendMapping();
        reverseMapping.setUser1(entity.getUser2());
        reverseMapping.setUser2(entity.getUser1());
        reverseMapping.setEntityStatus(EntityStatus.ACCEPTED);
        boolean res2 = userFriendsRepository.insertFriendMapping(reverseMapping);

        if(!res1||!res2)
        {
            return "Couldn't do that at this moment.";
        }
        return "Friend request accepted!";
    }

    public String declineFriendRequest(int user1, int user2)
    {
        UserFriendMapping entity = userFriendsRepository.findByKeys(user1,user2);
        boolean res = userFriendsRepository.deleteMapping(entity);
        if(!res)
        {
            return "Couldn't do that at this moment.";
        }
        return "Success!";
    }

    public List<UserDto> findAllUsers()
    {
        List<UserLogin> res = userLoginRepository.getAllUsers();
        List<UserDto> users = res.stream().map(user -> new UserDto(user.getId(),user.getName(),user.getUsername()))
                                            .toList();
        return users;
    }

    public List<UserDto> getFriendRequests(int id)
    {
        return userFriendsRepository.getFriendRequestsList(id);

    }

    public List<UserDto> getAllFriends(int id)
    {
        return userFriendsRepository.getUsersFriends(id);
    }
}

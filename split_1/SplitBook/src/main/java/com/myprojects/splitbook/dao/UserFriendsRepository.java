package com.myprojects.splitbook.dao;

import com.myprojects.splitbook.entity.UserFriendMapping;
import com.myprojects.splitbook.entity.dto.UserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class UserFriendsRepository {

    private static final String query_find_users_friends = "SELECT NEW com.myprojects.splitbook.entity.dto.UserDto(u.id, u.name, u.username) FROM UserLogin u " +
            "WHERE u.id IN (SELECT uf.user2 FROM UserFriendMapping uf WHERE uf.entityStatus = 1 AND uf.user1 = :userone)";
    private static final String query_find_by_keys = "SELECT uf FROM UserFriendMapping uf WHERE uf.user1 = :userone AND uf.user2 = :usertwo";
    private static final String query_find_friend_requests = "SELECT NEW com.myprojects.splitbook.entity.dto.UserDto(u.id, u.name, u.username) FROM UserLogin u " +
            "WHERE u.id IN (SELECT uf.user1 FROM UserFriendMapping uf WHERE uf.entityStatus = 0 AND uf.user2 = :usertwo)";
    @PersistenceContext
    EntityManager entityManager;

    public UserFriendMapping findByKeys(int user1, int user2)
    {
        TypedQuery<UserFriendMapping> query = entityManager.createQuery(query_find_by_keys,UserFriendMapping.class);
        query.setParameter("userone",user1);
        query.setParameter("usertwo",user2);
        UserFriendMapping friendMapping = query.getSingleResult();

        return friendMapping;
    }

    public List<UserDto> getUsersFriends(int userId)
    {
        TypedQuery<UserDto> query = entityManager.createQuery(query_find_users_friends, UserDto.class);
        query.setParameter("userone",userId);
        return query.getResultList();
    }

    public boolean insertFriendMapping(UserFriendMapping userFriendMapping)
    {
        UserFriendMapping res = entityManager.merge(userFriendMapping);
        return res!=null;
    }

    public boolean deleteMapping(UserFriendMapping userFriendMapping)
    {
        try {
            entityManager.remove(userFriendMapping);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    public List<UserDto> getFriendRequestsList(int id)
    {
        TypedQuery<UserDto> query = entityManager.createQuery(query_find_friend_requests, UserDto.class);
        query.setParameter("usertwo",id);
        return query.getResultList();
    }
}

package com.myprojects.splitbook;

import com.myprojects.splitbook.entity.UserFriendMapping;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    public void persistNewUserFriendMapping()
    {
        UserFriendMapping newRequest = new UserFriendMapping();
        newRequest.setUser1(39);
        newRequest.setUser2(40);

        entityManager.persist(newRequest);
    }
}

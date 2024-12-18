package com.myprojects.splitbook.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "UserFriendMapping")
@IdClass(UserFriendId.class)
public class UserFriendMapping {

    @Id
    private int user1;

    @Id
    private int user2;

    @Column
    private EntityStatus entityStatus;

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }
}


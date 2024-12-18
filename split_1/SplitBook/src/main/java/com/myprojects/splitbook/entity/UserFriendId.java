package com.myprojects.splitbook.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserFriendId implements Serializable {

    private int user1;
    private int user2;

    public UserFriendId()
    {
        super();
    }

    public UserFriendId(int user1, int user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFriendId that = (UserFriendId) o;
        return user1 == that.user1 && user2 == that.user2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }
}

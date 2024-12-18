package com.myprojects.splitbook.entity.dto;

public class UserDto {

    private int uid;
    private String name;
    private String username;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDto()
    {
        super();
    }

    public UserDto(int uid, String name, String username) {
        this.uid = uid;
        this.name = name;
        this.username = username;
    }
}

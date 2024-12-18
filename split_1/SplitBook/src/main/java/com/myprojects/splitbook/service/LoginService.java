package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.UserLoginRepository;
import com.myprojects.splitbook.entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserLoginRepository userLoginRepository;

    public UserLogin getUserByUsername(String username)         //method to abstract dao interface
    {
        return userLoginRepository.getUserByUsername(username);
    }

    public String registerUser(UserLogin user)
    {
        if(getUserByUsername(user.getUsername())!=null)       //username already registered
        {
            return "Email already registered!";
        }
        UserLogin userLogin = userLoginRepository.insertUser(user);
        if(userLogin==null)
        {
            return "Error occurred while registering!";
        }
        return "User successfully registered. Your username is "+userLogin.getUsername();
    }

    public UserLogin generateUser(String email, String password, String name)
    {
        Random random = new Random();

        UserLogin newUser = new UserLogin();
        newUser.setName(name);
        newUser.setEmail(email.toLowerCase());                  //to avoid duplicates
        newUser.setPassword(password);
        String nameCheck;

        do{
            nameCheck = name.replaceAll("\\s", "").toLowerCase()+random.nextInt(1000);    //generating a random unique username until unique username is found
        }while (getUserByUsername(nameCheck)!=null);

        newUser.setUsername(nameCheck);
        newUser.setRole("USER");        //TODO : hardcoding for now;add other roles

        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin user = userLoginRepository.getUserByUsername(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}

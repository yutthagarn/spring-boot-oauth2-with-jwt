package com.example.springboottutorial;

import org.springframework.security.core.userdetails.User;

/**
 * Created by IntelliJ IDEA.
 * User: treepay
 * Date: 28 September 2018
 * Time: 11:01 AM
 **/
public class CustomUser extends User {

    private static final long serialVersionUID = 1L;

    public CustomUser(UserEntity user) {
        super(user.getUsername(), user.getPassword(), user.getGrantedAuthoritiesList());
    }

}

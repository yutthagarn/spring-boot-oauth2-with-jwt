package com.example.springboottutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: treepay
 * Date: 28 September 2018
 * Time: 11:12 AM
 **/
@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    OAuthDao oAuthDao;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntity;
        try {
            userEntity = oAuthDao.getUserDetails(username);
            return new CustomUser(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}

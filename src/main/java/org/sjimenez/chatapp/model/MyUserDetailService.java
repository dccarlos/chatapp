package org.sjimenez.chatapp.model;

import org.sjimenez.chatapp.dao.ChatDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private ChatDao chatDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("userdetailservice");
        final org.sjimenez.chatapp.model.User user = chatDao.selectUserByMail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        String password=user.getNickname();
        List<GrantedAuthority> galist = new ArrayList<GrantedAuthority>();
        galist.add(new SimpleGrantedAuthority("USER"));
        return new User(username, password,galist);
    }
}

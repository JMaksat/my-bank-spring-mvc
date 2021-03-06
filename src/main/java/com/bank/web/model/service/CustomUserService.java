package com.bank.web.model.service;

import com.bank.web.model.entity.UserRoles;
import com.bank.web.model.entity.Users;
import com.bank.web.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("customUserService")
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = usersRepository.findByLogin(s);
        User usrDetails = new User(user.getUserName(), user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user));
        return usrDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(Users user) {
        Collection<GrantedAuthority> result = new ArrayList<>();
        List<UserRoles> permissions = userRolesRepository.findByRole(user);
        for (UserRoles permission : permissions) {
            GrantedAuthority perm = new SimpleGrantedAuthority(permission.getUserRole());
            result.add(perm);
        }
        return result;
    }
}
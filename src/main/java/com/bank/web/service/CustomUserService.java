package com.bank.web.service;

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

class CustomUserService {}

/*@Service("customUserService")
public class CustomUserService implements UserDetailsService {

    @Autowired
    private AdmUssecService admUssecService;
    @Autowired
    private AdmEgrsecService admEgrsecService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AdmUssec user = admUssecService.findByLogin(s);
        User usrDetails = new User(user.getAusLogin(), user.getAusPass(),
                true,
                true,
                true,
                true,
                getAuthorities(user));
        return usrDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(AdmUssec user) {
        Collection<GrantedAuthority> result = new ArrayList<>();
        List<AdmLgrsec> permissions = admEgrsecService.findByIdgr(user.getAusIdgr()).getPermissions();
        for (AdmLgrsec permission : permissions) {
            String role = "ROLE_" + permission.getAlgIdmenu() + permission.getAlgAcces();
            GrantedAuthority perm = new SimpleGrantedAuthority(role);
            result.add(perm);
        }
        result.add(new SimpleGrantedAuthority("ROLE_USER"));
        return result;
    }
}*/
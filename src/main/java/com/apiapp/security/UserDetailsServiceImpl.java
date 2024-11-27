package com.apiapp.security;

import com.apiapp.models.User;
import com.apiapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findByEmail(username);
        if (findUser != null) {
            org.springframework.security.core.userdetails.User user =
                    new org.springframework.security.core.userdetails.User(
                            findUser.getEmail(), findUser.getPassword(),
                            findUser.isEnabled(),
                            findUser.isAccountNonExpired(),
                            findUser.isCredentialsNonExpired(),
                            findUser.isAccountNonLocked(),
                            findUser.getAuthorities());
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

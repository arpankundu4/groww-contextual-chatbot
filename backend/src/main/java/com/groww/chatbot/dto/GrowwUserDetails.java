package com.groww.chatbot.dto;

import com.groww.chatbot.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * user details class
 * implements UserDetails
 * overrides its methods & provides
 * some of our own fields, constructors & methods
 */

@Getter
public class GrowwUserDetails implements UserDetails {

    private final String name;
    private final String email;
    private final String userId;
    private final String password;
    private final List<GrantedAuthority> authorities;

    // constructor to initialize
    // with data from User class
    public GrowwUserDetails(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.userId = user.getId();
        this.password = user.getPassword();
        this.authorities = getAuthoritiesFromUser(user);
    }

    // method to fetch authorities (roles)
    // from User class
    private List<GrantedAuthority> getAuthoritiesFromUser(User user) {
        if(user.getRole().isBlank()) {
            return new ArrayList<GrantedAuthority>();
        } else {
            return Arrays.stream(user.getRole().split(","))
                         .map(String::trim)
                         .map(SimpleGrantedAuthority::new)
                         .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

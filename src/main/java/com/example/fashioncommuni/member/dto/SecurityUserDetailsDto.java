package com.example.fashioncommuni.member.dto;

import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SecurityUserDetailsDto implements UserDetails {
    @Delegate
    private UserDto userDto;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userDto.roleType().toString()));
    }

    @Override
    public String getPassword() {
        return userDto.password();
    }

    @Override
    public String getUsername() {
        return userDto.loginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

package com.example.fashioncommuni.member.dto;

import com.example.fashioncommuni.member.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
@Slf4j @Getter
@AllArgsConstructor
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
    public boolean isAccountNonExpired() {return true;
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

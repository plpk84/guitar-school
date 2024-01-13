package com.petproject.guitarschool.security;

import com.petproject.guitarschool.models.UserEntity;
import com.petproject.guitarschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );

    }
}

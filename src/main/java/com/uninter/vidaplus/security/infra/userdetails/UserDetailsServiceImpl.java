package com.uninter.vidaplus.security.infra.userdetails;

import com.uninter.vidaplus.security.domain.User;
import com.uninter.vidaplus.security.exception.UserNotFoundException;
import com.uninter.vidaplus.security.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserGateway userGateway;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userGateway.findByEmail(email).orElseThrow(() -> {
            log.warn("Usuario nao encontrado para o email: {}", email);
            return new UserNotFoundException();
        });

        return new UserDetailsImpl(user);
    }
}

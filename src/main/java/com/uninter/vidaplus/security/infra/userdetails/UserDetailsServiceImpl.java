package com.uninter.vidaplus.security.infra.userdetails;

import com.uninter.vidaplus.security.domain.User;
import com.uninter.vidaplus.security.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserGateway userGateway;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userGateway.findByEmail(username).orElseThrow(() -> new RuntimeException("erro ao buscar usuario")); //TODO tratar melhor esse erro
        return new UserDetailsImpl(user);
    }
}

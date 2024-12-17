package com.example.bus;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Keresd meg a felhasználót az adatbázisban
        User user = userRepository.findByUsername(username);

        // Ha a felhasználó nem található, akkor dobjuk az exception-t
        if (user == null) {
            throw new UsernameNotFoundException("Felhasználó nem található: " + username);
        }

        // A szerepeket egyszerűsítjük SimpleGrantedAuthority típusúra
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))  // A List<String> szerepeket a megfelelő formátumban adjuk hozzá
                .collect(Collectors.toList());

        // Visszaadjuk a UserDetails objektumot a felhasználó adataival és szerepeivel
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


}

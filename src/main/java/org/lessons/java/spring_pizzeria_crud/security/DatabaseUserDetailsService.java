package org.lessons.java.spring_pizzeria_crud.security;

import org.lessons.java.spring_pizzeria_crud.models.User;
import org.lessons.java.spring_pizzeria_crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cerco l'utente nel DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));

        // Trasformo l'utente del mio DB in un oggetto UserDetails capito da Spring
        return new DatabaseUserDetails(user);
    }
}

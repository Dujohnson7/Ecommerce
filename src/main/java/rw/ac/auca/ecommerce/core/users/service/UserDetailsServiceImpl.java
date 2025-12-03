package rw.ac.auca.ecommerce.core.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.repository.IUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName())),
                user.getUsername(),
                user.getId()
        );
    }

    public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
        private final String username;
        private final UUID id;

        public CustomUserDetails(String email, String password, List<SimpleGrantedAuthority> authorities, String username, UUID id) {
            super(email, password, authorities);
            this.username = username;
            this.id = id;
        }

        @Override
        public String getUsername() {
            return username;
        }
        public UUID getId(){
            return id;
        }

    }

}
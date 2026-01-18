package htw.webtech.WT_todo.business.service;

import htw.webtech.WT_todo.persistence.entity.UserEntity;
import htw.webtech.WT_todo.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity register(String usernameRaw, String passwordRaw) {
        String username = normalizeUsername(usernameRaw);

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new IllegalArgumentException("Username ist bereits vergeben");
        }

        String hash = passwordEncoder.encode(passwordRaw);
        return userRepository.save(new UserEntity(username, hash));
    }

    public UserEntity authenticate(String usernameRaw, String passwordRaw) {
        String username = normalizeUsername(usernameRaw);
        UserEntity user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException("Ungültige Login-Daten"));

        if (!passwordEncoder.matches(passwordRaw, user.getPasswordHash())) {
            throw new BadCredentialsException("Ungültige Login-Daten");
        }

        return user;
    }

    public UserEntity requireByUsername(String usernameRaw) {
        String username = normalizeUsername(usernameRaw);
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("User nicht gefunden"));
    }

    private String normalizeUsername(String usernameRaw) {
        if (usernameRaw == null) return "";
        return usernameRaw.trim();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User nicht gefunden"));

        return User.withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities("USER")
                .build();
    }
}

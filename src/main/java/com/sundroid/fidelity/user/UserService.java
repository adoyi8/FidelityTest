package com.sundroid.fidelity.user;

import com.sundroid.fidelity.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.security.user.name}")
    private String adminUserName;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Value("${spring.security.user.roles}")
    private String adminRole;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        if(s.equals(adminUserName)) {
            return User.builder().username(adminUserName).password(passwordEncoder.encode(adminPassword)).authorities(AppUserRole.ADMIN.name()).build();
        }
        else {
            Optional<AppUser> user = userRepository.findByEmail(s.toLowerCase());

            if (user.isPresent()) {
                return user.get();
            } else {
                throw new UsernameNotFoundException(String.format("Username[%s] not found"));
            }
        }
    }

    public AppUser saveUser(AppUser s){
        String encodedPassword = bCryptPasswordEncoder
                .encode(s.getPassword());

        s.setPassword(encodedPassword);
        s.setEmail(s.getEmail().toLowerCase());
        AppUser user = userRepository.save(s);
        return  user;
    }

    public boolean isUserExist(String email){
        boolean userExists = userRepository
                .findByEmail(email)
                .isPresent();
        return userExists;
    }

    public AppUser getUser(String s){
        Optional<AppUser> user = userRepository.findByEmail(s);
        return user.orElseThrow();
    }
    public AppUser updateUser(AppUser s){
        s.setEmail(s.getEmail().toLowerCase());
        AppUser user = userRepository.save(s);
        return  user;
    }
}

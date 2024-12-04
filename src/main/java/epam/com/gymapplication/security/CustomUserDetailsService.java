package epam.com.gymapplication.security;


import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.Optional;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;




    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));


    }

}

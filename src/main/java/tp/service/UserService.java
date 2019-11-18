package tp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tp.model.User;
import tp.repository.UserRepository;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveNewUser(User user) {
        user.setPassword(encodePassword(user));
        userRepository.save(user);
        log.info("saveNewUser() : {}", user.toString());
    }

    public String encodePassword(User user) {
        return bCryptPasswordEncoder.encode(user.getPassword());
    }

    public boolean updateUsername(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        } else {
            updateUser(user);
            return true;
        }
    }

    public void updateUser(User user) {
        user.setPassword(encodePassword(user));
        userRepository.save(user);
    }
}

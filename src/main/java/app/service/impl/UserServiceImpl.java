package app.service.impl;

import app.entity.Role;
import app.entity.User;
import app.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean register(User user, Model model) {
        User userFromDb = loadUserByUsername(user.getUsername());

        if(userFromDb != null) {
            model.addAttribute("message", "Пользователь уже зарегистрирован");
            model.addAttribute("user", user);
            return false;
        } else if (user.getUsername().length() < 4 || user.getPassword().length() < 4) {
            model.addAttribute("message", "Логин и пароль должны быть длиною не менее 4 символов");
            model.addAttribute("user", user);
            return false;
        }
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(
            new BCryptPasswordEncoder(11)
                .encode(user.getPassword())
        );
        userRepo.save(user);
        return true;
    }
}

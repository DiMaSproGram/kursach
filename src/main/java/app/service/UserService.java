package app.service;

import app.common.service.AbstractService;
import app.entity.HardwareEntity;
import app.entity.HardwareType;
import app.entity.Role;
import app.entity.User;
import app.repository.HardwareTypeRepo;
import app.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends AbstractService<User, UserRepo> implements UserDetailsService {

    public UserService(UserRepo repository) {
        super(repository);
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
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
        user.setDateCreated(new Date());
        user.setDateUpdated(new Date());
        user.setPassword(
            new BCryptPasswordEncoder(11)
                .encode(user.getPassword())
        );

        repository.save(user);
        return true;
    }

    public List<User> getAllBySearching(String search) {
        ArrayList<User> arrayList = (ArrayList<User>) repository.findAll();
        arrayList.removeIf(
            elem -> !elem.getUsername().replaceAll(" ", "").toLowerCase().contains(
                search.replaceAll(" ", "").toLowerCase()
            )
        );
        return arrayList;
    }
}

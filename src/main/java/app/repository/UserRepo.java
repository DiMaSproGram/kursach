package app.repository;

import app.common.repository.AbstractRepository;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends AbstractRepository<User> {
    User findByUsername(String username);
}

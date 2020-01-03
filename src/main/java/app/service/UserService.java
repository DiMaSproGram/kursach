package app.service;

import app.entity.User;

public interface UserService {
    void addUser(String nickname, int password);
    User findUserByUsername(String nickname);
    void save(User user);
}

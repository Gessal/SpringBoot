package server.service;

import server.model.User;

import java.util.List;

public interface UserService {
    User add(User user);
    User get(Long id);
    User set(User user);
    void delete(Long id);
    List<User> list();
    User getByName(String username);
    boolean existsByName(String username);
}

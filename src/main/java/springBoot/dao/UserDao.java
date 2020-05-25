package springBoot.dao;

import springBoot.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(Long id);
    User getByUserName(String username);
    void set(User user);
    void delete(Long id);
    List<User> list();
}

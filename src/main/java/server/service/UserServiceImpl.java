package server.service;

import server.dao.UserCrudRepository;
import server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserCrudRepository userCrudRepository;

    @Autowired
    public UserServiceImpl(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    @Transactional
    @Override
    public User add(User user) {
        return userCrudRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User get(Long id) {
        return userCrudRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    @Override
    public User getByName(String username) {
        return userCrudRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public User set(User user) {
        return userCrudRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userCrudRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list() {
        return (List<User>) userCrudRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByName(String username) {
        return userCrudRepository.existsByUsername(username);
    }
}

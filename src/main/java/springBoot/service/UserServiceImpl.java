package springBoot.service;

import springBoot.dao.UserDao;
import springBoot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User get(Long id) {
        return userDao.get(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getByName(String username) {
        return userDao.getByUserName(username);
    }

    @Transactional
    @Override
    public void set(User user) {
        userDao.set(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list() {
        return userDao.list();
    }
}

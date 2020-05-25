package springBoot.dao;

import springBoot.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.save(user);
    }

    @Override
    public User get(Long id) {
        Session session = entityManager.unwrap(Session.class);
        TypedQuery<User> query = session.createQuery("from User where id = :id");
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public User getByUserName(String username) {
        Session session = entityManager.unwrap(Session.class);
        TypedQuery<User> query = session.createQuery("from User where username = :username");
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public void set(User user) {
        Session session = entityManager.unwrap(Session.class);
        User u = session.get(User.class, user.getId());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setAge(user.getAge());
        u.setRoles(user.getRoles());
        u.setEnabled(user.getEnabled());
        session.update(u);
    }

    @Override
    public void delete(Long id) {
        Session session = entityManager.unwrap(Session.class);
         TypedQuery<User> query = session.createQuery("DELETE User WHERE id = :id");
         query.setParameter("id", id);
         query.executeUpdate();
    }

    @Override
    public List<User> list() {
        Session session = entityManager.unwrap(Session.class);
        TypedQuery<User> query=session.createQuery("from User");
        List<User> users = query.getResultList();
        return users;
    }
}

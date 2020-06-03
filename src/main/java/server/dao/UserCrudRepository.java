package server.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.model.User;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String name);
}

package springBoot.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBoot.model.User;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String name);
}

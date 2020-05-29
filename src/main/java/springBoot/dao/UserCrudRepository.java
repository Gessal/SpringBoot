package springBoot.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBoot.model.User;

import java.util.List;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    void deleteById(Long id);
}

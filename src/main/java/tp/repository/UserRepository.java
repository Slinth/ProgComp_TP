package tp.repository;

import org.springframework.data.repository.CrudRepository;
import tp.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);
    User findByEmail(String email);
}

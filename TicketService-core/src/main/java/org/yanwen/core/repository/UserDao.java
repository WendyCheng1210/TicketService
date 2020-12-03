package org.yanwen.core.repository;
import org.yanwen.core.domain.User;


import java.util.List;

public interface UserDao {
    User save(User user);
    List<User> getAllUsers();
    User getByID(Long Id);
    User getEagerBy(Long Id);
    void delete(User user);
}

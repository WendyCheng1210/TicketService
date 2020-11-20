package org.yanwen.repository;

import org.yanwen.model.Seat;
import org.yanwen.model.Status;
import org.yanwen.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    List<User> getAllUsers();
    User getByID(Long Id);
    User getEagerBy(Long Id);
    void delete(User user);
}

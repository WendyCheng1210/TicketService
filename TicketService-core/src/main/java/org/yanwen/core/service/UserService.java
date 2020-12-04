package org.yanwen.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yanwen.core.domain.User;
import org.yanwen.core.repository.UserDao;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User save(User user){
        return userDao.save(user);
    }

    public List<User> getUsers(){
        return userDao.getAllUsers();
    }

    public User getByID(Long Id){
        return userDao.getByID(Id);
    }
}

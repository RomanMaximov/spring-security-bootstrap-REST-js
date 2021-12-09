package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.User;

import java.util.List;

public interface UserService {
    UserDetails loadUserByUsername(String userName);
    User getUserByUsername(String userName);
    User getUserById(int id);
    void addUser(User user);
    void delete(int id);
    List<User> getAllUsers();
    void update(User user);
}

package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.User;
import java.util.List;

public interface IUserDAO {
    User insert(User user);
    User update(User user);
    void delete(Long id);
    List<User> getAll();
    User getByUsername(String username);
    User getById(Long id);
}

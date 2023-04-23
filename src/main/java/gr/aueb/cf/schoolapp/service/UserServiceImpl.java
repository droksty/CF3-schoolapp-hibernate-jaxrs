package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import gr.aueb.cf.schoolapp.service.util.LoggerUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
@RequestScoped
public class UserServiceImpl implements IUserService {

    @Inject
    private IUserDAO userDAO;

    @Override
    public User insertUser(UserDTO userToInsert) throws EntityAlreadyExistsException {
        User user;
        try {
            JPAHelper.beginTransaction();
            user = mapUser(userToInsert);
            if (userToInsert.getId() == null) {
                userDAO.insert(user);
            } else {
                throw new EntityAlreadyExistsException(User.class, user.getId());
            }
            JPAHelper.commitTransaction();
        } catch (EntityAlreadyExistsException e) {
            JPAHelper.rollbackTransaction();
//            LoggerUtil.getCurrentLogger().warning("Insert user - " + "rollback - entity already exists");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }

    @Override
    public User updateUser(UserDTO userToUpdate) throws EntityNotFoundException {
        User user;
        try {
            JPAHelper.beginTransaction();
            user = mapUser(userToUpdate);
            if (userDAO.getById(userToUpdate.getId()) == null) {
                throw new EntityNotFoundException(User.class, userToUpdate.getId());
            }
            userDAO.update(user);
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
//            LoggerUtil.getCurrentLogger().warning("Update rollback - Entity not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            if (userDAO.getById(id) == null) {
                throw new EntityNotFoundException(Teacher.class, id);
            }
            userDAO.delete(id);
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
//            LoggerUtil.getCurrentLogger().warning("Delete rollback - Entity not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<User> getAllUsers() throws EntityNotFoundException {
        List<User> userList;
        try {
            JPAHelper.beginTransaction();
            userList = userDAO.getAll();
            if (userList.size() == 0) {
                throw new EntityNotFoundException(List.class, 0L);
            }
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
//            LoggerUtil.getCurrentLogger().warning("Get Users rollback " +    "- User not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return userList;
    }

    @Override
    public User getUser(String username) throws EntityNotFoundException {
        return null;
    }

    @Override
    public User getUser(Long id) throws EntityNotFoundException {
        User user;
        try {
            JPAHelper.beginTransaction();
            user = userDAO.getById(id);
            if (user == null) {
                throw new EntityNotFoundException(User.class, id);
            }
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
//            LoggerUtil.getCurrentLogger().warning("Get user by id rollback " + "- User not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }

    private User mapUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}

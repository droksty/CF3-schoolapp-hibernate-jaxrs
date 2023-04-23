package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IUserService {
    User insertUser(UserDTO userDTO) throws EntityAlreadyExistsException;
    User updateUser(UserDTO userDTO) throws EntityNotFoundException;
    void deleteUser(Long id) throws EntityNotFoundException;
    List<User> getAllUsers() throws EntityNotFoundException;
    User getUser(String username) throws EntityNotFoundException;
    User getUser(Long id) throws EntityNotFoundException;
}

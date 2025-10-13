package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {
        userDao.create(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.read(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.readAll();
    }
}

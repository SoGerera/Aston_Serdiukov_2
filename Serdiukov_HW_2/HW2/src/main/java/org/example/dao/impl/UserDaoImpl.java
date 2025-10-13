package org.example.dao.impl;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("User created: {}", user);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error creating user", e);
            throw e;
        }
    }

    @Override
    public User read(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                logger.info("User read: {}", user);
            } else {
                logger.warn("User not found with id: {}", id);
            }
            return user;
        } catch (HibernateException e) {
            logger.error("Error reading user", e);
            throw e;
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            logger.info("User updated: {}", user);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error updating user", e);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                logger.info("User deleted with id: {}", id);
            } else {
                logger.warn("User not found for deletion: {}", id);
            }
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error deleting user", e);
            throw e;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> readAll() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("FROM User").list();
            logger.info("Read all users: {}", users.size());
            return users;
        } catch (HibernateException e) {
            logger.error("Error reading all users", e);
            throw e;
        }
    }
}

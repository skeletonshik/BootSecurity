package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<User> getUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public User findUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public void deleteUser(long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}

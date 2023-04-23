package gr.aueb.cf.schoolapp.dao;


import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
@RequestScoped
public class UserDAOImpl implements IUserDAO {

    @Override
    public User insert(User user) {
        EntityManager em = getEntityManager();
        em.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        getEntityManager().merge(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        User userToDelete = em.find(User.class, id);
        em.remove(userToDelete);
    }

    @Override
    public List<User> getAll() {
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getByUsername(String username) {
        EntityManager em = getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public User getById(Long id) {
        EntityManager em = getEntityManager();
        return em.find(User.class, id);
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }
}

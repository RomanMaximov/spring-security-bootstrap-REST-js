package web.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    private List<GrantedAuthority> getAuthoritiesEntities(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User myUser = getUserByUsername(userName);
        List<GrantedAuthority> roleList = getAuthoritiesEntities(myUser.getRoles());
        org.springframework.security.core.userdetails.User usd = new org.springframework.security.core.userdetails.User(myUser.getLogin(), myUser.getPassword(), roleList);
        return usd;
    }

    public void update(User user) {
        em.merge(user);
    }

    @Override
    public User getUserByUsername(String userName) {
        return em.createQuery("select u from User u where u.login = :usName", User.class)
                .setParameter("usName", userName)
                .getSingleResult();
    }

    @Override
    public User getUserById(int id) {
        return em.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select u from User u").getResultList();
    }
}

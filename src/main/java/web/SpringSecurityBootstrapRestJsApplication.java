package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringSecurityBootstrapRestJsApplication {

    static UserService userService;
    static RoleService roleService;

    @Autowired
    public SpringSecurityBootstrapRestJsApplication(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityBootstrapRestJsApplication.class, args);
        User user1 = new User();
        user1.setName("Bob");
        user1.setSurname("Smith");
        user1.setEmail("smith_b@gmail.com");
        user1.setLogin("admin");
        user1.setPassword("100");
        User user2 = new User();
        user2.setName("Jimm");
        user2.setSurname("Simmons");
        user2.setEmail("simmons_j@gmail.com");
        user2.setLogin("user");
        user2.setPassword("101");

        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        Set<Role> setStartRoles = new HashSet<>();
        setStartRoles.add(role1);
        setStartRoles.add(role2);
        roleService.saveRole(setStartRoles);
        user1.setRoles(roleService.getAllRoles());
        userService.addUser(user1);
        user2.setRoles(setStartRoles.stream().filter(r -> r.getName() == "ROLE_USER").collect(Collectors.toSet()));
        userService.addUser(user2);
    }

}

package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/user")
    public ResponseEntity<User> getAuthenticationAdmin(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Set<Role> roleForUpdateUser = new HashSet<>();
        user.getRoles().forEach((element) -> roleForUpdateUser.add(roleService.findRole(element)));
        user.setRoles(roleForUpdateUser);
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        if (user.getRoles().size() != 0) {
            Set<Role> roleForUpdateUser = new HashSet<>();
            user.getRoles().forEach((element) -> roleForUpdateUser.add(roleService.findRole(element)));
            user.setRoles(roleForUpdateUser);
        } else {
            user.setRoles(userService.getUserById(user.getId()).getRoles());
        }
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/admin/roles")
    public ResponseEntity<Iterable<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}

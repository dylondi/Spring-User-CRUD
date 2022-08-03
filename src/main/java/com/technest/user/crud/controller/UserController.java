package com.technest.user.crud.controller;

import com.technest.user.crud.model.User;
import com.technest.user.crud.service.UserNotFoundException;
import com.technest.user.crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Processes incoming CRUD User requests.
 */
@Controller
public class UserController {


    /**
     * Constructor Dependency Injection
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a List of all Users, adds the list as attribute to Model object
     * and returns index.html page to display User List.
     * @param model
     * @return String name of html file
     */
    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> userList = userService.listAll();
        model.addAttribute("userList", userList);

        return "index";
    }

    /**
     * Adds a newly created User object as attribute to Model object and returns
     * create_user.html page to display New User form.
     * @param model
     * @return String name of html file
     */
    @GetMapping("/users/create")
    public String createNewUserPage(Model model){
        model.addAttribute("user", new User());

        return "create_user";
    }

    /**
     * Saves User Object with UserService and returns a redirect to /users endpoint.
     * @param user
     * @return String name of endpoint
     */
    @PostMapping("/users/save")
    public String saveNewUser(User user){
        userService.save(user);

        return "redirect:/users";
    }

    /**
     * Updates User Object containing specified id with UserService and returns a
     * redirect to /users endpoint.
     * @param user
     * @return String name of endpoint
     */
    @PostMapping("/users/update/{id}")
    public String updateUser(User user){
        userService.save(user);

        return "redirect:/users";
    }

    /**
     * Retrieves the User object with the specified id, adds the user object as attribute
     * to Model and then returns the update_user.html to display Update User form.
     * @return String name of endpoint
     */
    @GetMapping("/users/update/{id}")
    public String updateUserPage(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){

        try{
            User user = userService.get(id);
            model.addAttribute("user", user);
            return "update_user";
        } catch(UserNotFoundException e){
            redirectAttributes.addFlashAttribute("message", "The user has not been found.") ;
            return "redirect:/users";
        }
    }

    /**
     * Deletes User object with specified id, if object exists. Otherwise, displays user not found message.
     * @return String name of endpoint
     */
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try{
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", "User deleted");
        } catch(UserNotFoundException e){
            redirectAttributes.addFlashAttribute("message", "The user has not been found.") ;
        }
        return "redirect:/users";

    }
}

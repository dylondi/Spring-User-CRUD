package com.technest.user.crud.controller;

import com.technest.user.crud.model.User;
import com.technest.user.crud.repository.UserRepository;
import com.technest.user.crud.service.UserNotFoundException;
import com.technest.user.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    public UserController(UserRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public String landingPage(Model model){
        List<User> userList = userService.listAll();
        model.addAttribute("userList", userList);

        return "index";
    }

    @GetMapping("/users/create")
    public String createNewUserPage(Model model){
        model.addAttribute("user", new User());

        return "create_user";
    }

    @PostMapping("/users/save")
    public String saveNewUser(User user){
        userService.save(user);

        return "redirect:/users";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(User user){
        userService.save(user);

        return "redirect:/users";
    }

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

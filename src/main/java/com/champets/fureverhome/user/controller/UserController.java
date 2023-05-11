package com.champets.fureverhome.user.controller;

import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.dto.UserDto;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("users/new")
    public String saveUser(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("users/new")
    public String createUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user-create";
    }

    @GetMapping("/user-register")
    public String listUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user-registration";
    }
    @PostMapping("users/{userId}/edit")
    public String updateUser(@PathVariable("userId") Long userId,
                            @Valid @ModelAttribute("club") UserDto user){
        user.setId(userId);
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("users/{userId}/edit")
    public String editUser(@PathVariable("userId") Long userId, Model model){
        UserDto user = userService.findById(userId);
        model.addAttribute("user",user);
        return "user-edit";
    }

//    @PostMapping
//    public User saveUser(@RequestBody User user){
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
}
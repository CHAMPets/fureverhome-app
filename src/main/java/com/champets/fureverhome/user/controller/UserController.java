package com.champets.fureverhome.user.controller;

import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.model.dto.RegistrationDto;
import com.champets.fureverhome.user.repository.UserRepository;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "login";
        }

    @GetMapping("/admin/navigation")
    public String showAdminNavigation(Model model) {
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "admin/admin-navigation";
    }


    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult result, Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/login?fail";
        }
        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/login?fail";
        }
        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "login";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }
    @GetMapping("/forgotPasswordPage")
    public String forgotPasswordPage(){
        return "forgot-password";
    }

    @GetMapping("/resetPasswordPage/{id}")
    public String resetPasswordPage(@PathVariable Long id, Model m){
        m.addAttribute("id",id);
        return "reset-password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email,
                                 @RequestParam String username,
                                 HttpSession session) {
        UserEntity user = userRepository.findByUsernameAndEmail(username,email);
        if(user!=null){
            return "redirect:/resetPasswordPage/" + user.getId();
        } else {
        session.setAttribute("msg","Invalid username and email");
            return "forgot-password";
        }

    }
    @PostMapping("/changePassword")
    public String resetPassword(@RequestParam String password,
                                @RequestParam Long id,
                                HttpSession session) {
        UserEntity user = userRepository.findUserById(id);
        String encryptPassword=passwordEncoder.encode(password);
        user.setPassword(encryptPassword);

        UserEntity updateUser=userRepository.save(user);
        if(updateUser!=null){
            session.setAttribute("msg", "Password Change Successfully");
        }
        return "redirect:/forgotPasswordPage";
    }
}

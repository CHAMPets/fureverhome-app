package com.champets.fureverhome.about;

import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AboutController {
    @Autowired
    private UserService userService;

    @GetMapping("/about")
    public String displayAbout(Model model) {
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "user/user-about";
    }
}
